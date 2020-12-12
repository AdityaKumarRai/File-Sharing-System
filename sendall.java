import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
class sendall extends JFrame implements ActionListener
{
JList l,l1;
JScrollPane jsp,jsp1;
JLabel lab;
JButton left,right,next,selall,close;
Vector sel=new Vector();
Vector all=new Vector();
chatform chform;
String sender;
//form1 f;
sendall(chatform chform,String sender,Vector v)
{
super("SENDING TO GROUP");
this.chform=chform;
this.sender=sender;
all=v;
show();
this.all=all;
setLocation(75,75);
setSize(600,450);
Container c=getContentPane();
c.setLayout(null);
l=new JList(all);
l1=new JList();
//l.setSelectionBackground(Color.blue);
//l1.setSelectionBackground(Color.blue);
lab=new JLabel("SELECT   USERS");
lab.setBounds(220,20,350,40);
lab.setFont(new Font("",Font.BOLD,18));
lab.setForeground(Color.blue);
int v1=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
jsp=new JScrollPane(l,v1,h);
jsp.setBounds(100,110,100,240);
jsp1=new JScrollPane(l1,v1,h);
jsp1.setBounds(400,110,100,240);
right=new JButton("==>");
right.setBounds(280,150,60,40);
left=new JButton("<=="); 
left.setBounds(280,250,60,40);
c.setFont(new Font("courier",Font.BOLD,18));
//left.setFont(new Font("",Font.BOLD,18));
next=new JButton("Next");
selall=new JButton("Select All");
close=new JButton("Close");
selall.setBounds(120,370,100,30);
close.setBounds(260,370,100,30);
next.setBounds(400,370,100,30);
next.addActionListener(this);
selall.addActionListener(this);
close.addActionListener(this);
left.addActionListener(this);
right.addActionListener(this);
c.add(lab);
c.add(jsp);
c.add(jsp1);
c.add(left);
c.add(right);
c.add(selall);
c.add(close);
c.add(next);
}
public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==right&& !l.isSelectionEmpty())
{
sel.addElement((String)all.elementAt(l.getSelectedIndex()));
all.removeElementAt(l.getSelectedIndex());
l.setListData(all);
l1.setListData(sel);
}
if(ae.getSource()==left&& !l1.isSelectionEmpty())
{
all.addElement((String)sel.elementAt(l1.getSelectedIndex()));
sel.removeElementAt(l1.getSelectedIndex());
l.setListData(all);
l1.setListData(sel);
}
if(ae.getSource()==selall)
{
for(int i=0;i<all.size();i++)
sel.insertElementAt((String)all.elementAt(i),i);
all.removeAllElements();
l.setListData(all);
l1.setListData(sel);
}
if(ae.getSource()==next&&sel!=null)
{
setVisible(false);
//f=new form1(chform,null,sender,sel);
}
}
/*public static void main(String ds[])
{
Vector v=new Vector();
v.addElement(ds[0]);
v.addElement(ds[1]);
v.addElement(ds[2]);
new sendall(v);
} */
}



