import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
class killall extends JFrame implements ActionListener
{
JList l,l1;
JScrollPane jsp,jsp1;
JLabel lab;
JButton kill,close;
chatform chform;
String sender;
Vector v=new Vector();
killall(chatform chform,Vector v)
{
super("KILLING  GROUP");
this.chform=chform;
this.v=v;
show();
setLocation(75,75);
setSize(600,450);
Container c=getContentPane();
c.setLayout(null);
l=new JList(v);
lab=new JLabel("SELECT   USERS");
lab.setBounds(220,20,350,40);
lab.setFont(new Font("",Font.BOLD,18));
lab.setForeground(Color.blue);
int v1=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
jsp=new JScrollPane(l,v1,h);
jsp.setBounds(100,110,100,240);
c.setFont(new Font("courier",Font.BOLD,18));
kill=new JButton("KILL");
close=new JButton("CLOSE");
kill.setBounds(300,150,100,30);
close.setBounds(300,200,100,30);
close.addActionListener(this);
kill.addActionListener(this);
c.add(lab);
c.add(jsp);
c.add(close);
c.add(kill);
}
public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==kill&& !l.isSelectionEmpty())
{
try
{
int ar[]=l.getSelectedIndices();
for(int i=0;i<ar.length;i++)
{
byte byt[]=(v.elementAt(ar[i])+" mustquit").getBytes();
chform.out.write(byt,0,byt.length);
}
setVisible(false);
}catch(Exception e){System.out.println(e);}
}
if(ae.getSource()==close)
setVisible(false);
}
/*public static void main(String ds[])
{
Vector v=new Vector();
v.addElement(ds[0]);
v.addElement(ds[1]);
v.addElement(ds[2]);
new killall(v);
} */
}



