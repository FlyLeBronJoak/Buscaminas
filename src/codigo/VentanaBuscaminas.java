package codigo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Joaquin Sierra
 */
public class VentanaBuscaminas extends javax.swing.JFrame {

    int filas = 15;
    int columnas = 20;
    int numeroMinas = 30;
    Icon bandera = new ImageIcon(getClass().getResource("/images/bandera.png"));
    Icon bomba = new ImageIcon(getClass().getResource("/images/minaa.png"));

    Boton[][] arrayBotones = new Boton[filas][columnas];

    /**
     * Crea VentanaBuscaminas
     */
    public VentanaBuscaminas() {
        initComponents();
        setSize(800, 600);
        jDialog1.setSize(600, 400);
        setLocationRelativeTo(null);
        jDialog1.setLocationRelativeTo(null);
        
        perdedor.setVisible(false);       
        getContentPane().setLayout(new GridLayout(filas, columnas));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Boton boton = new Boton(i, j);
                getContentPane().add(boton);
                boton.setBorder(null);
                arrayBotones[i][j] = boton;
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        botonPulsado(evt);
                    }
                });

            }
        }
        ponMinas(numeroMinas);
        cuentaMinas();
        

    }

    //este método es llamado cada vez que hacemos clic en un botón
    private void botonPulsado(MouseEvent e) {

        Boton miBoton = (Boton) e.getComponent();
        if (e.getButton() == MouseEvent.BUTTON3 && miBoton.isEnabled()) {
            miBoton.setIcon(bandera);
        }
        if (e.getButton() == MouseEvent.BUTTON1 && miBoton.getText().equals("")) {
            if (miBoton.getMina() == 1) {
                miBoton.setIcon(bomba);
                miBoton.setOpaque(false);
                miBoton.setBackground(Color.RED);
                jDialog1.setVisible(true);
                perdedor.setVisible(true);
                jButton1.setVisible(true);
                GameOver();
//                this.setEnabled(false);
                
            } else if (miBoton.getNumeroMinasAlrededor() == 0) {
                miBoton.setFocusPainted(false);
                Alrededor(miBoton);
            } else {
                miBoton.setText(String.valueOf(miBoton.getNumeroMinasAlrededor()));
                miBoton.setEnabled(false);
                miBoton.setFocusPainted(false);
            }
        }
    }

    private void Alrededor(Boton boton) {

        if (boton.getNumeroMinasAlrededor() == 0) {
            boton.setEnabled(false);
            for (int k = -1; k < 2; k++) {
                for (int m = -1; m < 2; m++) {
                    if ((boton.getI() + k >= 0) && (boton.getJ() + m >= 0) && (boton.getI() + k < filas) && (boton.getJ() + m < columnas)) {
                        if (arrayBotones[boton.getI() + k][boton.getJ() + m].isEnabled()) {
                            if (arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor() == 0) {
                                arrayBotones[boton.getI() + k][boton.getJ() + m].setEnabled(false);
                                Alrededor(arrayBotones[boton.getI() + k][boton.getJ() + m]);
                            } else if (arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor() != 0) {
                                arrayBotones[boton.getI() + k][boton.getJ() + m].setEnabled(false);
                                arrayBotones[boton.getI() + k][boton.getJ() + m].
                                        setText(String.valueOf(arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor()));
                            }
                        }
                    }
                }
            }
        }

    }

    private void ponMinas(int numeroMinas) {
        Random r = new Random();
        for (int i = 0; i < numeroMinas; i++) {
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);

            //TODO hay que hacer una version que chequee si en la casilla seleccionada ya hay una mina, 
            //porque en ese caso tiene que buscar otra
            arrayBotones[f][c].setMina(1);
            
        }
    }

    //metodo para que cada boton colcule el numero de minas que tiene alrededor 
    private void cuentaMinas() {
        // TODO falta por hacer que calcule las minas del borde exterior
        int minas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int m = -1; m < 2; m++) {
                        if ((i + k >= 0) && (j + m >= 0) && (i + k < filas) && (j + m < columnas)) {
                            minas = minas + arrayBotones[i + k][j + m].getMina();
                        }
                    }
                }
                arrayBotones[i][j].setNumeroMinasAlrededor(minas);

                //TODO comentar la siguiente parte para que no aparezcan los numeros al iniciar la partida
//                if (arrayBotones[i][j].isEnabled()) {
//                    if (arrayBotones[i][j].getMina() == 0) {
//                        arrayBotones[i][j].setText(String.valueOf(minas));
//                    }
//                }
                minas = 0;
//                
                if(arrayBotones[i][j].getMina() == 1 ){
                    arrayBotones[i][j].setText("");
                }
                if (arrayBotones[i][j].getNumeroMinasAlrededor() == 0) {
                    arrayBotones[i][j].setEnabled(true);
                }
            }
        }
    }
    
    private void GameOver(){
        for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                if(arrayBotones[i][j].getMina()==0){
                    if(arrayBotones[i][j].getNumeroMinasAlrededor()!=0){
                        arrayBotones[i][j].setText(String.valueOf(arrayBotones[i][j].getNumeroMinasAlrededor()));
                    }
                }
                else {
                    arrayBotones[i][j].setIcon(bomba);
                }
                arrayBotones[i][j].setEnabled(true);
            }
            
        }
    }
    private void Reset(){
        VentanaBuscaminas reset = new VentanaBuscaminas();
        reset.setVisible(true);
        dispose();
    }
    private void Close(){
        dispose();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        perdedor = new javax.swing.JLabel();

        jDialog1.setMaximumSize(new java.awt.Dimension(600, 600));
        jDialog1.setMinimumSize(new java.awt.Dimension(600, 600));
        jDialog1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jDialog1.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 130, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Reiniciar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jDialog1.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 130, 40));

        perdedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/perder.jpg"))); // NOI18N
        perdedor.setMaximumSize(new java.awt.Dimension(800, 600));
        perdedor.setMinimumSize(new java.awt.Dimension(800, 600));
        perdedor.setPreferredSize(new java.awt.Dimension(800, 600));
        jDialog1.getContentPane().add(perdedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 520));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        Reset();
        jDialog1.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Close();
        jDialog1.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

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
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaBuscaminas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel perdedor;
    // End of variables declaration//GEN-END:variables
}
