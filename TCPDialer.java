import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
class TCPDialer extends Frame implements ActionListener,Runnable
{
Socket s;
BufferedReader br;
BufferedWriter bw;
//List list;
TextArea ta;
TextField text,server,port;
Button sendBut,exitBut,connectBut,closeconBut;
Label l;
public TCPDialer(String m)
{
super(m);

setSize(400,250);
setLocation(0,0);
setResizable(true);
setBackground(new Color(192,192,192));
this.setLayout(new GridLayout(2,1));
Panel panels[]=new Panel[2];
panels[0]=new Panel();
panels[1]=new Panel();

panels[0].setLayout(new BorderLayout());
panels[1].setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

//list=new List();
ta=new TextArea();
ta.setEditable(false);
text=new TextField(15);
server=new TextField(15);
port=new TextField(5); port.setText("80");
sendBut=new Button("Send");
exitBut=new Button("Exit");
connectBut=new Button("Dial");
closeconBut=new Button("Hang up");
connectBut.addActionListener(this);
closeconBut.addActionListener(this);
sendBut.addActionListener(this);
exitBut.addActionListener(this);
l=new Label("Please enter URL or IP and port below and press Dial");

//panels[0].add(list);
panels[0].add(ta);
panels[1].add(l);
panels[1].add(server);
panels[1].add(port);
panels[1].add(connectBut);
panels[1].add(closeconBut);
panels[1].add(text);
panels[1].add(sendBut);
panels[1].add(exitBut);

add(panels[0]);
add(panels[1]);

setVisible(true);

}
public void run()
{
try
{
String a=new String();
while(true)
{
if((a=br.readLine())!=null)
{
//list.addItem(a);
ta.append(a+"\n");
}
}
}
catch(Exception e)
{}
}

public static void main(String args[])throws Exception
{
new TCPDialer("TCPDialer");
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource().equals(exitBut))
{
System.exit(0);
}
if(ae.getSource().equals(sendBut))
{
ta.setText("");

try
{

bw.write(text.getText());
bw.newLine();
bw.newLine();
bw.flush();

}
catch(Exception e)
{
l.setText("Not connected");
}
}
if(ae.getSource().equals(connectBut))
{

try
{
s=new Socket(server.getText(),Integer.parseInt(port.getText()));
br=new BufferedReader(new InputStreamReader(new BufferedInputStream(s.getInputStream())));
bw=new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(s.getOutputStream())));

Thread th=new Thread(this);
th.start();
l.setText("Connected");
}

catch(Exception e)
{
l.setText("Could not connect with server");
}
}
if(ae.getSource().equals(closeconBut))
{
try
{
s.close();
l.setText("Disconnected");

}
catch(NullPointerException e)
{
l.setText("Dialer is not connected with any server");

}
catch(Exception e)
{
l.setText("Could not hang up the connection");

}

}
}
}