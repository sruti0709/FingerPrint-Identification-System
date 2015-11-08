import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.*;
import java.awt.image.Kernel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.imageio.ImageIO;
import javax.swing.*;



public class FPinterface extends JFrame {

BufferedImage img,img1;
JTextField timg,timg1;
JButton ideal,test,match,graph;
JLabel lname,Imlabel;
JPanel panel1,panel2,panel3,panel4,panel5;
File file = null;
File file1 = null;
String path="";
String path1="";
int Iw1,Ih1,Iw2,Ih2;
graphpoint[] m1=new graphpoint[2];
float MATCH;


public FPinterface(String Title)throws InterruptedException {
        super(Title);
        setSize(800,600);
         Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
         int x = (d.width - getSize().width) / 2;
         int y = (d.height - getSize().height) / 2;
         setLocation(x, y);
         setResizable(false);
        Container container = getContentPane();

        timg=new JTextField(20);
        timg1=new JTextField(20);
        timg.setBounds(20,20,140,20);
        timg1.setBounds(20,20,140,20);


        lname=new JLabel("FINGERPRINT MATCHING...");
        
        ideal = new JButton("IDEAL IMAGE");ideal.addActionListener(new ButtonListener());
        test  = new JButton("TEST IMAGE");test.addActionListener(new ButtonListener());
        match = new JButton("MATCH");match.addActionListener(new ButtonListener());
        //graph = new JButton("GRAPH");graph.addActionListener(new ButtonListener());
         match.setEnabled(false);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        container.add(BorderLayout.EAST, panel1);

        panel1.add(timg);
        panel1.add(ideal);
        panel1.add(timg1);
        panel1.add(test);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        container.add(BorderLayout.SOUTH, panel2);

        panel2.add(match);
        //panel2.add(graph);
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        container.add(BorderLayout.WEST, panel3);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        container.add(BorderLayout.NORTH, panel4);
        
        panel4.add(lname);
        
        JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout());
        container.add(BorderLayout.CENTER, panel5);

        addWindowListener(new WindowEventHandler());

        //show();
        setVisible(true);

      }

      class WindowEventHandler extends WindowAdapter {
           public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
          }
          
          
          
          public static void main(String arg[]) {

                try{
                  new FPinterface("FINGERPRINT");
                 }
                  catch(InterruptedException e){}

           }
    class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JButton button = (JButton) e.getSource();

      if (button.equals(ideal)) {
      JFileChooser chooser = new JFileChooser();
      chooser.addChoosableFileFilter(new ImageFileFilter());
      int returnVal = chooser.showOpenDialog(null);

      if(returnVal == JFileChooser.APPROVE_OPTION) {
          file = chooser.getSelectedFile();
          path=file.getPath();
          timg.setText(file.getName());

          try
          {
           img = ImageIO.read(file);
           m1[0]=new graphpoint(img);
           m1[0].gaussian(40);
           m1[0].thinning();
           m1[0].feature("IDEAL_IMAGE");
           m1[0].tree();
          }

          catch (IOException exp) { }

          repaint();
         }
        //displayPanel.repaint();
      } else if (button.equals(test)) {
      repaint();
          JFileChooser chooser = new JFileChooser();
          chooser.addChoosableFileFilter(new ImageFileFilter());
          int returnVal = chooser.showOpenDialog(null);

       if(returnVal == JFileChooser.APPROVE_OPTION) {
          file = chooser.getSelectedFile();
          path=file.getPath();
          timg1.setText(file.getName());

          try
          {
           img1 = ImageIO.read(file);
           m1[1]=new graphpoint(img1);
           m1[1].gaussian(40);
           m1[1].thinning();
           m1[1].feature("TEST_IMAGE");
           m1[1].tree();
          }
         catch (IOException exp) { }
          repaint();
          Iw1=img.getWidth(null);//System.out.println(Iw1);
          Ih1=img.getHeight(null);//System.out.println(Ih1);
          Iw2=img1.getWidth(null);//System.out.println(Iw2);
          Ih2=img1.getHeight(null);//System.out.println(Ih2);
          if((Iw1 == Iw2) && (Ih1 == Ih2))
              match.setEnabled(true);
         }
        //displayPanel.repaint();
      } else if (button.equals(match)) {
             MATCH =  m1[0].match(m1[1]);
             
        if(MATCH!=0.0f){
          int t=(int)((MATCH/m1[0].value)*100);
          JOptionPane.showMessageDialog((Component) e.getSource(),"PERCENTAGE OF MATCHING : "+t+"%");
         }
         else
           JOptionPane.showMessageDialog((Component) e.getSource(),"NO MATCH FOUND!!!!!");

      }/* else if (button.equals(graph)) {

      }*/
    }
    }
     public void paint(Graphics g)
    {

        if(img != null){
          g.drawImage(img, 20, 100, null);
         }
       if(img1 != null){

        g.drawImage(img1, 400, 100, null);
       }
    }
 }


 class ImageFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
          if (file.isDirectory()) return false;
          String name = file.getName().toLowerCase();
          return (name.endsWith(".jpg") );
     }
    public String getDescription() { return "Images ( *.jpg )"; }
  }
