package recycleMachine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerMouseListner implements MouseListener {
	String timeBefore="";
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource().equals(Server.tableConReq)){
			int row = Server.tableConReq.getSelectedRow();
			if(row>-1 ){
				String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
            	System.out.println(timeBefore+" -- "+time);
            	if(!timeBefore.equals(time)){
            		timeBefore=time;
            		System.out.println("IN ----  ");
            		ConnectionRequest request = Server.connectionRequests.get(row);
            		Clients c = request.getClient();
            		c.setName("Client "+Server.connectedClients.size()+1+" | "+c.getIpAddress());
            		c.setIpAddress(c.getIpAddress());
            		request.setStatus(1);
            		Server.connectedClients.add(c);
			
            		request.setClient(c);
            		Server.connectionRequests.remove(row);
            		Server.connectionRequests.add(request);
            	}
			}
		}else if(arg0.getSource().equals(Server.tableResetReq)){
			int row = Server.tableResetReq.getSelectedRow();
			if(row>-1 ){
				String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
            	System.out.println(timeBefore+" -- "+time);
//            	if(!timeBefore.equals(time)){
            		timeBefore=time;
            		System.out.println("IN ----  ");
            		ResetRequests request = Server.resetRequests.get(row);
            		Clients c = request.getClient();
            		for(Clients cl :Server.connectedClients){
            			if(c.getIpAddress().equals(cl.getIpAddress())){
            				Server.connectedClients.remove(cl);
            				break;
            			}
            		}
            		c.setName("Client "+Server.connectedClients.size()+1+" | "+c.getIpAddress());
            		request.setStatus(1);			
            		request.setClient(c);
            		Server.resetRequests.remove(row);
            		Server.resetRequests.add(request);
//            	}
			}
		}
		Server.loadDefaults();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(arg0.getSource().equals(Server.tableConReq)){
//			System.out.println("mouseEntered");
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(arg0.getSource().equals(Server.tableConReq)){
//			System.out.println("mouseExited");
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getSource().equals(Server.tableConReq)){
//			System.out.println("mousePressed");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
