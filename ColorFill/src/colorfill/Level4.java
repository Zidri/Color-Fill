/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorfill;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author joannaahannigan
 */
public class Level4 extends javax.swing.JFrame {

    //grid arraylist
    ArrayList<gameBlock> algb = new ArrayList();

    //player piece
    gamePlayer player = new gamePlayer();

    //start block
    int startBlock = 0;
    //current block
    int playerBlock = 0;

    //type of move
    char moveType = 'r';

    //timer
    boolean timestart = false;
    Timer timer;

    int timeSec = 0;
    int timeMin = 0;

    //continue playing
    boolean cont = true;

    public Level4() {
        initComponents();
        setTitle("Color Fill");
        setLocationRelativeTo(null);

        gameSetup();
        mazeSetup();
        playerSetUp();

        this.requestFocus();
    }

    void timerStart() {

        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                timeSec++;
                if (timeSec > 60) {
                    timeSec = 0;
                    timeMin++;
                }
                if (timeSec < 10) {
                    jlTime.setText(timeMin + ":0" + timeSec + "");
                } else {
                    jlTime.setText(timeMin + ":" + timeSec + "");
                }

            }
        });

        timestart = true;
        timer.start();
    }

    //set up game path
    void gameSetup() {

        jlpGame.removeAll();
        algb.clear();

        int x = 0;
        int y = 0;

        int blockCnt = (jlpGame.getWidth() / 15) * jlpGame.getHeight();

        for (int i = 1; i <= blockCnt; i++) {
            gameBlock gb = new gameBlock();

            gb.setNum(i);
            gb.setXloc(x);
            gb.setYloc(y);
            gb.setLocation(x, y);
            gb.setVisible(true);
            gb.setPathColor(Color.black);

            //add control to arraylist
            algb.add(gb);

            //add to panal
            jlpGame.add(gb, 3);

            x = x + gb.getWidth();

            //control rows + cols
            if ((i % 20) == 0) {
                y = y + gb.getHeight();
                x = 0;
            }
        }//end of for

        jlpGame.repaint();

    }

    void mazeSetPath(int i) {
        gameBlock block = algb.get(i);
        block.setPathColor(Color.white);
        algb.get(i).setIsPath(true);
    }

    //maze set up
    void mazeSetup() {

        //left top        
        int start = 20;
        int end = 17;

        for (int i = 0; i < 4; i++) {
            //right        
            for (int m = 0; m < end; m++) {
                start++;
                mazeSetPath(start);
            }
            //down
            for (int k = 0; k < end; k++) {
                start += 20;
                mazeSetPath(start);
            }
            end-=2;
            //left
            for (int j = 0; j < end; j++) {
                start--;
                mazeSetPath(start);
            }
            //up
            for (int n = 0; n < end; n++) {
                start -= 20;
                mazeSetPath(start);
            }
            end-=2;            
        }

    }

    //set up player piece
    void playerSetUp() {

        //find first white block    
        int startX = 0, startY = 15;

        for (int i = 0; i < algb.size(); i++) {

            if (algb.get(i).isPath) {
                //get location           
                startX = algb.get(i).getXloc();
                startY = algb.get(i).getYloc();
                player.setOverlappedBlock(algb.get(i).getNum());
                playerBlock = i;
                startBlock = i;
                //set block to color
                algb.get(i).pathColor = Color.MAGENTA;
                algb.get(i).isColored = true;
                //leave loop
                break;
            }
        }

        player.setSize(15, 15);
        player.setLocation(startX, startY);
        player.setVisible(true);
        player.setCol(Color.pink);
        jlpGame.add(player, 10);
        jlpGame.repaint();
    }

    void playerMove() {

        switch (moveType) {
            case 'r':
                player.setLocation(player.getX() + 15, player.getY());
                break;
            case 'l':
                player.setLocation(player.getX() - 15, player.getY());
                break;
            case 'u':
                player.setLocation(player.getX(), player.getY() - 15);
                break;
            case 'd':
                player.setLocation(player.getX(), player.getY() + 15);
                break;
            default:
                break;
        }

        player.setVisible(true);
        jlpGame.repaint();

        colorCheck();

    }

    void winCheck() {
        int cnt = 0;
        for (int i = 0; i < algb.size(); i++) {
            if (algb.get(i).isPath && !algb.get(i).isColored) {
                cnt++;
            }
        }
        if (cnt == 0) {
            //stop timer
            timer.stop();
            //create message frame
            JFrame msgFrame = new JFrame();

            //make modal -> must be delt with before it goes away
            msgFrame.setAlwaysOnTop(true);

            //display win
            JOptionPane.showMessageDialog(
                    msgFrame,
                    "Congrats!\n Your Time: " + jlTime.getText(),
                    "Maze Complete",
                    JOptionPane.QUESTION_MESSAGE);

            cont = false;

            jRadioButtonMenuItem5.doClick();
        }
    }

    void colorCheck() {
        switch (moveType) {
            case 'r':
                playerBlock += 1;
                algb.get(playerBlock).setPathColor(algb.get(startBlock).getPathColor());
                algb.get(playerBlock).setIsColored(true);
                winCheck();
                break;
            case 'l':
                playerBlock -= 1;
                algb.get(playerBlock).setPathColor(algb.get(startBlock).getPathColor());
                algb.get(playerBlock).setIsColored(true);
                winCheck();
                break;
            case 'u':
                playerBlock -= 20;
                algb.get(playerBlock).setPathColor(algb.get(startBlock).getPathColor());
                algb.get(playerBlock).setIsColored(true);
                winCheck();
                break;
            case 'd':
                playerBlock += 20;
                algb.get(playerBlock).setPathColor(algb.get(startBlock).getPathColor());
                algb.get(playerBlock).setIsColored(true);
                winCheck();
                break;
            default:
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlpGame = new javax.swing.JLayeredPane();
        jlTime = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem5 = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jlpGame.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jlpGame.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout jlpGameLayout = new javax.swing.GroupLayout(jlpGame);
        jlpGame.setLayout(jlpGameLayout);
        jlpGameLayout.setHorizontalGroup(
            jlpGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        jlpGameLayout.setVerticalGroup(
            jlpGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        jlTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTime.setText("Time");

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem1.setText("Quit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem2.setText("Restart");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Levels");

        jRadioButtonMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jRadioButtonMenuItem1.setText("Level 1");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jRadioButtonMenuItem2.setText("Level 2");
        jMenu2.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jRadioButtonMenuItem3.setText("Level 3");
        jRadioButtonMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jRadioButtonMenuItem3);

        jRadioButtonMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jRadioButtonMenuItem4.setSelected(true);
        jRadioButtonMenuItem4.setText("Level 4");
        jRadioButtonMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jRadioButtonMenuItem4);

        jRadioButtonMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jRadioButtonMenuItem5.setText("Level 5");
        jRadioButtonMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jRadioButtonMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jlTime, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlpGame, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jlTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlpGame, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (!timestart) {
            timerStart();
        }

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                //move up until next key (algb.get(i-20)) is black (level 3)

                while (algb.get(playerBlock - 20).isPath && cont) {
                    moveType = 'u';
                    playerMove();
                }
                break;
            case KeyEvent.VK_DOWN:
                //move down until next key (algb.get(i+20)) is black
                while (algb.get(playerBlock + 20).isPath && cont) {
                    moveType = 'd';
                    playerMove();
                }
                break;
            case KeyEvent.VK_LEFT:
                //move left until next key (algb.get(i-1)) is black
                while (algb.get(playerBlock - 1).isPath && cont) {
                    moveType = 'l';
                    playerMove();
                }
                break;
            case KeyEvent.VK_RIGHT:
                //move right until next key (algb.get(i+1)) is black
                while (algb.get(playerBlock + 1).isPath && cont) {
                    moveType = 'r';
                    playerMove();
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        //go to level 1
        Level1 Level1 = new Level1();

        Level1.setLocationRelativeTo(null);
        Level1.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //go to level 2
        Level2 Level2 = new Level2();

        Level2.setLocationRelativeTo(null);
        Level2.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jRadioButtonMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem3ActionPerformed
        //go to level 3
        Level3 Level3 = new Level3();

        Level3.setLocationRelativeTo(null);
        Level3.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jRadioButtonMenuItem3ActionPerformed

    private void jRadioButtonMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem4ActionPerformed
        //make new level 4
        Level4 Level4 = new Level4();

        Level4.setLocationRelativeTo(null);
        Level4.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jRadioButtonMenuItem4ActionPerformed

    private void jRadioButtonMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem5ActionPerformed
        //go to level 5
        Level5 Level5 = new Level5();

        Level5.setLocationRelativeTo(null);
        Level5.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jRadioButtonMenuItem5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Level4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Level4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Level4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Level4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Level4().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem5;
    private javax.swing.JLabel jlTime;
    private javax.swing.JLayeredPane jlpGame;
    // End of variables declaration//GEN-END:variables

}
