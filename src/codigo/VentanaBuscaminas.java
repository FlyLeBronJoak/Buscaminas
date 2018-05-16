package codigo;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;

/**
 * @author Gonzalo Izuzquiza
 */
public class VentanaBuscaminas extends javax.swing.JFrame {

    int filas = 15;
    int columnas = 20;
    int numeroMinas;
    
    Boton[][] arrayBotones = new Boton[filas][columnas];
    
    /**
     * Creates new form VentanaBuscaminas
     */
    public VentanaBuscaminas(){
        initComponents();
        setSize(800,600);
        getContentPane().setLayout(new GridLayout(filas, columnas));
        for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                Boton boton = new Boton(i, j);
                getContentPane().add(boton);
                boton.setBorder(null);
                arrayBotones[i][j] = boton;
               boton.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent evt){
                        botonPulsado(evt);
                    }
                });
               
            }
        }
        ponMinas(30);
        cuentaMinas();
    }
    
    private void botonPulsado(MouseEvent e){
        
        Boton miBoton = (Boton) e.getComponent();
        if(e.getButton() == MouseEvent.BUTTON3){
            miBoton.setText("?");
        } else{
            
        }
        
    }
    
    private void ponMinas(int numeroMinas){
        Random r = new Random();
        for (int i=0; i<numeroMinas; i++){
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);
            
            //TODO hay que hacer una version que chequee si en la casilla seleccionada ya hay una mina, 
            //porque en ese caso tiene que buscar otra
            arrayBotones[f][c].setMina(1);
            arrayBotones[f][c].setText("M");     
        }
    }
    
    //metodo para que cada boton colcule el numero de minas que tiene alrededor 
    private void cuentaMinas(){
        // TODO falta por hacer que calcule las minas del borde exterior
        int minas = 0;     
        for(int i=0; i<filas; i++){
            for (int j=0; j<columnas; j++){
                if((i>0) && (j>0) && (i < filas - 1) && (j < columnas - 1)){
                    minas += arrayBotones[i-1][j-1].getMina(); // la mina de arriba a la izquierda
                    minas += arrayBotones[i][j-1].getMina(); // la mina de la izquierda
                    minas += arrayBotones[i+1][j-1].getMina(); // la mina de abajo izquierda
                
                    minas += arrayBotones[i-1][j].getMina(); // la mina de encima
                    minas += arrayBotones[i+1][j].getMina(); // la mina de abajo
                    
                    minas += arrayBotones[i-1][j+1].getMina(); // la mina de arriba a la derecha
                    minas += arrayBotones[i][j+1].getMina(); // la mina de la derecha
                    minas += arrayBotones[i+1][j+1].getMina(); // la mina de abajo derecha
                }
                arrayBotones[i][j].setNumeroMinasAlrededor(minas);
                 //TODO comentar la siguiente parte para que no aprezcan los numeros al iniciar
                if(arrayBotones[i][j].getMina() == 0){
                    arrayBotones[i][j].setText(String.valueOf(minas));
                }
                minas=0;
            }   
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
    // End of variables declaration//GEN-END:variables
}
