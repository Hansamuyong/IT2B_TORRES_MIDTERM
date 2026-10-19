package com.scc;

import com.scc.dao.PassageDao;
import com.scc.ui.TollMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PassageDao passageDao = new PassageDao();
            TollMenu menu = new TollMenu(passageDao, scanner);
            menu.display();
        }
    }
}