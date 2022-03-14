package com.example.menojang;

import java.util.ArrayList;

public class Define {

    public String lstFileTitle = "";
    public String lstFileTime = "";
    public String lstFileMemo = "";
    public int chek = 0;
    public ListViewAdapter adapter = new ListViewAdapter();

    private static Define instance;
    public static Define ins() {
        if (instance == null) {
            instance = new Define();
        }
        return instance;
    }
}
