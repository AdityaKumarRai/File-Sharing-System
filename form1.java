import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
public class form1 extends JFrame implements ActionListener
{
	JTextField tf;
	JTextArea ta;
	JLabel l;
	JButton send,exit;
	JScrollPane jsp;
        chat ch;
        String rece,snd;
        int x,app;
        form1(String rece,String snd,chat ch)
        {
        super(snd+" to "+rece);
        this.ch=ch;
        this.rece=rece;
        this.snd=snd;
        setLocation(150,100);
	Container c=getContentPane();
	c.setLayout(new GridLayout(3,1));
        setSize(300,300);
	setVisible(true);
	JPanel p1=new JPanel(new FlowLayout());
                  JPanel p2=new JPanel(new BorderLayout());
                  JPanel p3=new JPanel(new FlowLayout());
  l=new JLabel("Sender "+snd);
  l.setFont(new Font("helvatica",Font.BOLD,20));
  p1.add(l);
  ta=new JTextArea();
  tf=new JTextField();
ta.disable();
int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
jsp=new JScrollPane(ta,v,h);
  p2.add(jsp,BorderLayout.CENTER);
  p2.add(tf,BorderLayout.SOUTH);
  send=new JButton("SEND");
  exit=new JButton("EXIT");
  send.addActionListener(this);
  exit.addActionListener(this);
  p3.add(send);
  p3.add(exit);
  c.add(p1);
  c.add(p2);
  c.add(p3);
}


public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==send)
{
try
{
String s="theinstantmessage "+rece+" "+snd+" "+":"+tf.getText()+"%*";
ch.out.write(s.getBytes());
ta.append(snd+" : "+tf.getText()+"\n");
tf.setText(" ");
}catch(Exception e){System.out.println(e);}
} 
if(ae.getSource()==exit)
{
setVisible(false);
}
}
/*public static void main(String ds[])
{
new form1();
} */
}  
  

  
	

	
