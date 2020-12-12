import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
public class chat extends JFrame implements Runnable,ActionListener,MouseListener
{
JButton send,exit;
JTextArea ta,ta1;
JTextField tf;
Label l1;
JScrollPane jsp,jsp1;
DataInputStream rin;
DataOutputStream out;
int n=0;
String user,mess;
Label l[]=new Label[10];
JPanel p2;
Vector v=new Vector();
//chat c1;
static int z=0;
byte b[]=new byte[50];
form1 f;
chat()
{
super("CONNECTED USERS INFORMATION");
try
{
Socket cli=new Socket("10.8.1.15",143);
rin=new DataInputStream(cli.getInputStream());
out=new DataOutputStream(cli.getOutputStream());
InetAddress inet=InetAddress.getLocalHost();
user=inet.getHostAddress();
byte x[]=("Theusername, "+user).getBytes();
System.out.println(new String(x));
out.write(x,0,x.length);
Container c=getContentPane();
c.setLayout(new BorderLayout());
show();
setSize(600,425);
setLocation(75,75);
JPanel p=new JPanel(new BorderLayout());
JPanel p1=new JPanel(new FlowLayout());
p2=new JPanel(new GridLayout(10,1,0,10));

ta=new JTextArea();
tf=new JTextField();
ta.setFont(new Font("",Font.BOLD,18));
ta.disable();
int v1=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
jsp=new JScrollPane(ta,v1,h);
p.add(jsp,BorderLayout.CENTER);
p.add(tf,BorderLayout.SOUTH);
send=new JButton("SEND ALL");
exit=new JButton("EXIT");
p1.add(send);
p1.add(exit);
exit.addActionListener(this);
send.addActionListener(this);
l1=new Label("                        ALL CONNECTED USERS");
l1.setFont(new Font("helvatica",Font.BOLD,22));
l1.setForeground(Color.darkGray);
/*ta.addMouseListener(new MouseAdapter()
{
public void mouseEntered(MouseEvent me){
ta.disable();
}
public void mouseExited(MouseEvent me){
ta.enable();}
});*/
         rin.read(b);
         String str=new String(b).trim();
         System.out.println(str);
         StringTokenizer s=new StringTokenizer(str," ");
        while(s.hasMoreTokens())
        {
        String t=s.nextToken();
        if(!t.equals("allusers"))
        {
        ta.append(t+"  :Presently Connected"+"\n");
        v.addElement(t);
        }
        }
for(int i=0;i<10;i++)
{
l[i]=new Label("");
p2.add(l[i]);
}
jsp1=new JScrollPane(p2,v1,h);
c.add(l1,BorderLayout.NORTH);
c.add(p,BorderLayout.CENTER);
c.add(p1,BorderLayout.SOUTH);
c.add(jsp1,BorderLayout.EAST);
addlabel();
Thread t=new Thread(this);
t.start();
}catch(Exception e){System.out.println("anand1 "+e);}
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==exit)
{
System.exit(0);
}
if(ae.getSource()==send)
{
try
{
String st="themessagetoall "+user+" "+user+" :"+tf.getText()+"%*";
byte by[]=st.getBytes();
out.write(by,0,by.length);
tf.setText("");                     
}catch(Exception e){System.out.println(e);}
} 
}
public void run()
{
String msg;
try
{
while(true)
{
byte b[]=new byte[200];
        if(rin.read(b)>=1)
        {
        msg=new String(b).trim();
//        System.out.println("Comming message :"+msg);
        if(msg.endsWith("allusers"))
        {
        StringTokenizer s=new StringTokenizer(msg," ");
        int x=s.countTokens();
        for(int i=1;i<x-1;i++)
        s.nextToken();
        String u=s.nextToken();
        ta.append(u+"  :Recently Connected"+"\n");
        v.addElement(u);
        addlabel();
        }
        else
        if(msg.endsWith("exited123"))
        {
        StringTokenizer s=new StringTokenizer(msg," ");
        String t=s.nextToken();
        ta.append(t+"  : Disconnected"+"\n");
        v.removeElement(t);
        addlabel();
        }
        }
}
}catch(Exception e){System.out.println("anand2 "+e);}
}
void addlabel()
{
for(int i=0;i<v.size();i++)
{
String t="   "+(String)v.elementAt(i)+"   ";
l[i].setFont(new Font("",Font.BOLD,16));
l[i].setText(t);
if(user.equals((String)v.elementAt(i)))
{l[i].setForeground(Color.red);
continue;}

l[i].setForeground(Color.blue);
l[i].addMouseListener(this);
}
for(int j=v.size();j<10;j++)
l[j].setText("");
}
public void mousePressed(MouseEvent me){}
public void mouseEntered(MouseEvent me){setCursor(Cursor.HAND_CURSOR);}
public void mouseExited(MouseEvent me){setCursor(Cursor.DEFAULT_CURSOR);}
public void mouseClicked(MouseEvent me){
if(me.getClickCount()==1)
{
Label l=(Label)me.getComponent();
f=new form1(l.getText(),user,this);
}

}
public void mouseReleased(MouseEvent me){}
/*public static void main(String ds[])
{
new chat(ds[0]);
} */
public static void main(String args[]) throws Exception {
chat c1=new chat();
}
}

