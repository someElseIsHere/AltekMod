package com.swa.client.helper;

import java.util.List;

public class McSrvStatData {
    public boolean online;
    public PlayersData players;

    public static class PlayersData {
        public List<PlayerData> list;
        public int max;
        public int online;
        public static class PlayerData {
            public String name;
        }
    }
}
