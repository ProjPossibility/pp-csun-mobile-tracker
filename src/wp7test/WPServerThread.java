package wp7test;

import net.AddressClass;
import net.ParamClass;
import net.WrapperClass;
import net.WrapperClass.wrapperTypes;
import net.sockets.MyServerSocket;
import net.sockets.SocketFailure;
import edu.GEORoute;
import edu.GoogleGPS;

public class WPServerThread {
	

	/**
	 * The server socket for this thread
	 */
	protected MyServerSocket socket; // Socket
	/**
	 * Wrapper class for sending and receiving data
	 */
	private String tmpWrapper;
	
	private AddressClass startAddr, endAddr;
	private ParamClass curParams;
	private GEORoute curRoute;

	/**
	 * Constructor We need to pass the database through this constructor
	 * 
	 * @param socket
	 *            The socket this Thread will use
	 */
	public WPServerThread(MyServerSocket socket) {
		this.socket = socket;
		startAddr = null;
		endAddr = null;
		curParams = null;
		curRoute = null;
	}

	/**
	 * Thread Invoked
	 */
	public void run() {
		System.out.println("Thread started for client: "
				+ socket.getInetAddress() + ".");
		// Perform Read
		doRead();

		// Close the Socket
		try {
			socket.closeSocket();
		} catch (Exception e) {
			System.out.println("Other Socket Close Exception: " + e);
		}
	}

	/**
	 * Private utility function used to get data from the socket
	 */
	private void doRead() {
		// Read a Wrapper Infinitely long
		socket.setTimeout(2000);
		
		boolean runFlag = true;
		while (runFlag) {
			try {
				tmpWrapper = new String(socket.readRawData()); // Read the data
				System.out.println(tmpWrapper);
				
			} catch (Exception e) {
				System.out.println("Exception reading/writing  Streams: " + e);
				break;
			}
		}
	}
	
	private void parseGPS(String httpData)
	{
		//
		int startIdx, endIdx;
		String startDelim = "GET ";
		String endDelim = "HTTP/1.1";
		String tmpStr;
		
		startIdx = httpData.indexOf(startDelim);
		endIdx = httpData.indexOf(endDelim);
		
		if( startIdx > -1 && endIdx > -1 )
		{
			//
		}
	}

	/**
	 * Send Data utility method Child objects call this method using the
	 * reference they were given when instantiated
	 * 
	 * @param data
	 *            Data to wrap and send
	 * @param dType
	 *            The data type of the data we are sending
	 */
	public void sendData(Object data, WrapperClass.wrapperTypes dType) {
		WrapperClass tmpWrap = new WrapperClass();
		tmpWrap.setData(data);
		tmpWrap.setDType(dType);

		socket.sendData(tmpWrap);
	}

	/**
	 * Gets data from the socket
	 * 
	 * @return WrapperClass retrieved from the socket
	 */
	public WrapperClass getData() {
		try {
			return socket.readData();
		} catch (SocketFailure e) {
			socket.closeSocket();
			System.exit(1);
			return null;
		}
	}
}

