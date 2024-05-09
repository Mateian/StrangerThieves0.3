package PaooGame.main;

import PaooGame.Game;

import java.io.*;

public class Config {
    Game gp;

    public Config(Game gp) {
        this.gp = gp;
    }
    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // FX volume
            bw.write(String.valueOf(gp.fx.volumeScale));
            bw.newLine();

            bw.close();
        } catch(IOException e) {
            System.out.println("Error: Unable to write file.");
        }
    }
    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s;

            // Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // FX volume
            s = br.readLine();
            gp.fx.volumeScale = Integer.parseInt(s);

            br.close();
        } catch(IOException e) {
            System.out.println("Error: Unable to read file.");
        }
    }
}
