package com.swa.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.swa.AltekMod;
import com.swa.client.helper.McSrvStatData;
import com.swdteam.client.gui.GuiDMU;
import com.swdteam.util.Graphics;
import com.swdteam.util.IOUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class AltMUScreen extends Screen {
    public static final String IP = "simplefactionsmc.net";

    public RotatingCubeMapRenderer panorama;
    public Screen previousScreen;
    public GuiDMU.LoadState loadState;
    public McSrvStatData serverData;
    public static final ServerInfo SERVER = new ServerInfo(Language.getInstance().get("altekmod.altmu.name"), IP, false);
    public static final int RANDOM_INT = new Random().nextInt(100);

    public AltMUScreen(Screen previousScreen) {
        super(new TranslatableText("altekmod.altmu.gui.name"));
        this.loadState = GuiDMU.LoadState.LOADING;
        this.panorama = new RotatingCubeMapRenderer(TitleScreen.PANORAMA_CUBE_MAP);
        this.previousScreen = previousScreen;
        new Thread(() -> {
            String s = IOUtil.readFileURL("https://api.mcsrvstat.us/3/" + IP);
            if (s != null) {
                try {
                    this.serverData = AltekMod.GSON.fromJson(s, McSrvStatData.class);
                    if (!this.serverData.online || this.serverData.players.list == null) {
                        this.loadState = GuiDMU.LoadState.ERROR;
                    } else if (this.serverData.players.list.isEmpty()) {
                        this.loadState = GuiDMU.LoadState.EMPTY;
                    } else {
                        this.loadState = GuiDMU.LoadState.LOADED;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    this.loadState = GuiDMU.LoadState.ERROR;
                }
            } else {
                this.loadState = GuiDMU.LoadState.ERROR;
            }

        }).start();
    }

    public void init() {
        this.addButton(new ButtonWidget(this.width / 2 + 2, this.height / 2 + 75, 94, 20, new TranslatableText("altekmod.altmu.gui.button.join"),
                (button) -> this.client.openScreen(new ConnectScreen(this, this.client, SERVER)))
        );
        this.addButton(new ButtonWidget(this.width / 2 - 96, this.height / 2 + 75, 94, 20, new TranslatableText("altekmod.altmu.gui.button.back"),
                (button) -> MinecraftClient.getInstance().openScreen(this.previousScreen)
        ));
        super.init();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        LiteralText tooltip = null;
        fill(matrixStack, 0, 0, this.width, this.height, -1);
        this.panorama.render(delta, MathHelper.clamp(1.0F, 0.0F, 1.0F));
        MinecraftClient.getInstance().textureManager.bindTexture(GuiDMU.DMU_GUI);
        this.drawTexture(matrixStack, this.width / 2 - 128, this.height / 2 - 99, 0, 0, 256, 199);

        int playersOnline = 0;
        int maxPlayers = 0;

        if (this.serverData != null && this.serverData.online) {
            playersOnline = this.serverData.players.online;
            maxPlayers = this.serverData.players.max;
        }

        if (playersOnline >= maxPlayers) {
            drawCenteredText(matrixStack, this.textRenderer, new TranslatableText("altekmod.altmu.gui.message.full"), this.width / 2, this.height / 2 - 74, -1);
        } else {
            drawCenteredText(matrixStack, this.textRenderer, new TranslatableText("altekmod.altmu.gui.message.player_count", playersOnline, maxPlayers), this.width / 2, this.height / 2 - 74, -1);
        }

        int size;
        if (this.loadState == GuiDMU.LoadState.LOADING) {
            drawCenteredText(matrixStack, this.textRenderer, new TranslatableText("altekmod.altmu.gui.message.loading"), this.width / 2, this.height / 2 - 8, -664254);
        } else if (this.loadState == GuiDMU.LoadState.ERROR) {
            String s;
            if (RANDOM_INT >= 95){
                s = "altekmod.altmu.gui.message.error_eggy";
            }else {
                s = "altekmod.altmu.gui.message.error";
            }
            drawCenteredText(matrixStack, this.textRenderer, new TranslatableText(s), this.width / 2, this.height / 2 - 8, -664254);
        } else if (this.loadState == GuiDMU.LoadState.EMPTY) {
            drawCenteredText(matrixStack, this.textRenderer, new TranslatableText("altekmod.altmu.gui.message.empty"), this.width / 2, this.height / 2 - 8, -664254);
        } else {
            size = this.serverData.players.max;
            if (playersOnline > maxPlayers) {
                size = playersOnline;
            }
            if (size > 60) {
                size = 60;
            }
            for (int i = 0; i < size; ++i) {
                int x = this.width / 2 - 98 + i % 10 * 20;
                int y = this.height / 2 - 55 + i / 10 * 20;
                boolean valid = true;
                Identifier tex;
                if (this.serverData.players.list == null || i >= this.serverData.players.list.size()) {
                    tex = Graphics.getPlayerHead("dmu_placeholder_def_skin_" + i);
                    MinecraftClient.getInstance().textureManager.bindTexture(tex);
                    valid = false;
                } else {
                    if (mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16) {
                        tooltip = new LiteralText(this.serverData.players.list.get(i).name);
                    }
                    tex = Graphics.getPlayerHead(this.serverData.players.list.get(i).name);
                    MinecraftClient.getInstance().textureManager.bindTexture(tex);
                }
                RenderSystem.enableBlend();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, valid ? 1.0F : 0.35F);
                drawTexture(matrixStack, x, y, 0.0F, 0.0F, 16, 16, 16, 16);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
            }
        }

        super.render(matrixStack, mouseX, mouseY, delta);
        if (tooltip != null) {
            this.renderTooltip(matrixStack, tooltip, mouseX, mouseY);
        }

    }
}
