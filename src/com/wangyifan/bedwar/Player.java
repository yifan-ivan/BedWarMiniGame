package com.wangyifan.bedwar;

public class Player {
    int id;
    Boolean sleep = true;
    Boolean bed = true;
    Boolean eliminated = false;
    Boolean shield = false;
    Boolean lock = false;
    Boolean cannon = false;
    Boolean ItalianCannon = false;
    Boolean bedProtected = false;
    String location;
    int health = 1;

    public Player(int id) {
        this.id = id;
        this.location = "Home#" + this.id;
    }

    public void die() {
        if (!this.bed) {
            this.eliminate();
        }
        this.sleep = true;
        this.health = 1;
        location = "Home#" + this.id;
        this.shield = this.lock = this.cannon = this.ItalianCannon = false;
        System.out.println(Utils.colorString("Player #" + this.id + " died!", "red"));
    }

    public void getup() {
        this.sleep = false;
    }

    public void getDamage(int damageCount) {
        if (this.shield) {
            this.shield = false;
            System.out.println(Utils.colorString("Player #" + this.id + " lost his shield!", "red"));
        }
        if (health - damageCount <= 0) {
            this.die();
        }
        else {
            health -= damageCount;
        }
    }

    public void getItem(String item) {
        switch (item) {
            case "shield":
                this.shield = true;
                break;
            case "cannon":
                this.cannon = true;
                break;
            case "lock":
                this.lock = true;
                break;
            case "ItalianCannon":
                this.ItalianCannon = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item + "!");
        }
        System.out.println(Utils.colorString("Player #" + this.id + " get a " + item, "green"));
    }

    public void teleport(String location) {
        this.location = location;
        System.out.println(Utils.colorString("Player #" + this.id + " went to " + location, "green"));
    }

    public void protectBed() {
        if (this.lock) {
            this.lock = false;
            this.bedProtected = true;
            System.out.println(Utils.colorString("Player #" + this.id + " protected his bed!", "green"));
        }
    }

    public void eliminate() {
        this.eliminated = true;
        Main.playerNum --;
        System.out.println(Utils.colorString("Player #" + this.id + " has been eliminated!", "red"));

    }

}
