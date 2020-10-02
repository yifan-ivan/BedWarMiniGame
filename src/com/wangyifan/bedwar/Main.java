package com.wangyifan.bedwar;

import jdk.jshell.execution.Util;

import java.util.Random;
import java.util.Scanner;
//import com.wangyifan.bedwar.Utils;

public class Main {
    static Player[] players = new Player[100001];
    static int playerNum = 2;
    public static void printStatus() {
        for (int i = 0; i < playerNum; i++) {
            Player player = players[i];
            System.out.printf("\033[33m=============Player #%d=============\033[0m\n", player.id);
            System.out.println("Sleeping: " + Utils.colorBoolean(player.sleep));
            System.out.println("Has bed: " + Utils.colorBoolean(player.bed));
            System.out.println("Has shield: " + Utils.colorBoolean(player.shield));
            System.out.println("Has lock: " + Utils.colorBoolean(player.lock));
            System.out.println("Has cannon: " + Utils.colorBoolean(player.cannon));
            System.out.println("Has ItalianCannon: " + Utils.colorBoolean(player.ItalianCannon));
            System.out.println("Has cannon: " + Utils.colorBoolean(player.cannon));
            System.out.println("Current location: " + player.location);
            System.out.println("Is bed Protected: " + Utils.colorBoolean(player.bedProtected));
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        playerNum = sc.nextInt();
        for (int i = 0; i < playerNum; i++) {
            players[i] = new Player(i+1);
        }
//        System.out.println("=============Game Started!=============");
        while (true) {
//            System.out.println("Current status: ");
            printStatus();
            Random random = new Random();
            int randomPlayerIndex = random.nextInt(playerNum);
            Player player = players[randomPlayerIndex];
            System.out.println(Utils.colorString("This round: Player #" + player.id, "blue"));
            System.out.println("Please select you operation:");
            if (player.sleep) {
                System.out.println("1. Get up");
            }
            if (! player.sleep) {
                System.out.println("2. Teleport");
                System.out.println("3. Attack");
                if (player.lock) {
                    System.out.println("4. Use the lock");
                }
                if (player.cannon) {
                    System.out.println("5. Use the cannon");
                }
                if (player.ItalianCannon) {
                    System.out.println("6. Use the Italian Cannon");
                }
                if (player.location.equals("Scientific Base") || player.location.equals("War Base") ||
                player.location.equals("Electron Store")) {
                    System.out.println("7. Buy");
                }
            }
            int mode = sc.nextInt();

            switch (mode) {
                case 1:
                    // Get up
                    player.getup();
                    break;
                case 2:
                    // Teleport
                    System.out.println("1. Home");
                    System.out.println("2. Scientific Base");
                    System.out.println("3. War Base");
                    System.out.println("4. Electron Store");
                    int input = sc.nextInt();
                    switch (input) {
                        case 1:
                            System.out.printf("Please enter the home number you want to go to: (1~%d)\n", playerNum);
                            input = sc.nextInt();
                            player.teleport("Home#" + input);
                            break;
                        case 2:
                            player.teleport("Scientific Base");
                            break;
                        case 3:
                            player.teleport("War Base");
                            break;
                        case 4:
                            player.teleport("Electron Store");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + input);
                    }
                    break;

                case 3:
                    // Attack
                    for (int i = 0; i < playerNum; i++) {
                        if (i+1 == player.id) {
                            continue;
                        }
                        if (players[i].location.equals(player.location)) {
                            System.out.println("Attack #" + players[i].id + "? 1: Yes, 2: No");
                            input = sc.nextInt();
                            if (input == 1) {
                                System.out.println("You attacked #" + players[i].id + ".");
                                if (players[i].shield) {
                                    players[i].shield = false;
                                } else {
                                    players[i].getDamage(1);
                                }
                                break;
                            }
                        }
                    }
                    break;

                case 4:
                    // Protect bed
                    player.protectBed();
                    break;

                case 5:
                    // Attack with cannon
                    for (int i = 0; i < playerNum; i++) {
                        if (i+1 == player.id) {
                            continue;
                        }
                        System.out.println("Attack #" + players[i].id + " with the cannon? 1: Yes, 2: No");
                        input = sc.nextInt();
                        if (input == 1) {
                            System.out.println("You attacked #" + players[i].id + " with the cannon.");
                            players[i].getDamage(1);
                            player.cannon = false;
                            break;
                        }
                    }
                    break;

                case 6:
                    // Attack with Italian cannon
                    for (int i = 0; i < playerNum; i++) {
                        if (i+1 == player.id) {
                            continue;
                        }
                        System.out.println("Attack #" + players[i].id + " with the Italian cannon? 1: Yes, 2: No");
                        input = sc.nextInt();
                        if (input == 1) {
                            System.out.println("You attacked #" + players[i].id + " with the Italian cannon.");
                            players[i].getDamage(1);
                            if (players[i].bedProtected) {
                                players[i].bedProtected = false;
                                System.out.println("Player #" + players[i].id + " lost his protection of his bed!");
                            } else {
                                players[i].bed = false;
                                System.out.println("Player #" + players[i].id + " lost his bed!");
                            }
                            player.teleport("Home#" + player.id);
                            players[i].sleep = false;
                            player.cannon = false;
                            break;
                        }
                    }
                    break;

                case 7:
                    // Buy
                    if (player.location.equals("Scientific Base")) {
                        System.out.println("Please enter the item you want to buy:");
                        System.out.println("1. Shield");
                        input = sc.nextInt();
                        switch (input) {
                            case 1:
                                player.getItem("shield");
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + input);
                        }
                    } else if (player.location.equals("War Base")) {
                        System.out.println("Please enter the item you want to buy:");
                        System.out.println("1. Cannon");
                        System.out.println("2. Italian Cannon");
                        input = sc.nextInt();
                        switch (input) {
                            case 1:
                                player.getItem("cannon");
                                break;
                            case 2:
                                player.getItem("ItalianCannon");
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + input);
                        }
                    } else if (player.location.equals("Electron Store")) {
                        System.out.println("Please enter the item you want to buy:");
                        System.out.println("1. lock");
                        input = sc.nextInt();
                        switch (input) {
                            case 1:
                                player.getItem("lock");
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + input);
                        }
                    } else {
                        Utils.colorString("Your current location don't support buying!", "red");
                    }
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + mode);
            }

        }
    }
}
