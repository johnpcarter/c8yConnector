package jc.cumulocity.tools;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import com.jc.wm.introspection.APIInfo;
import com.jc.wm.introspection.PackageIntrospector;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.app.b2b.server.ServerStartupNotifier;
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void envProperties (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(envProperties)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional C8Y_BASEURL
		// [i] field:0:optional C8Y_BOOTSTRAP_TENANT
		// [i] field:0:optional C8Y_BOOTSTRAP_USER
		// [i] field:0:optional C8Y_BOOTSTRAP_PASSWORD
		// [i] field:0:optional C8Y_TENANT
		// [o] field:0:required C8Y_BOOTSTRAP_TENANT
		// [o] field:0:required C8Y_BOOTSTRAP_USER
		// [o] field:0:required C8Y_BOOTSTRAP_PASSWORD
		// [o] field:0:required C8Y_BASEURL
		// [o] field:0:optional C8Y_TENANT
		// [o] field:0:optional C8Y_USER
		// [o] field:0:optional C8Y_PASSWORD
		IDataCursor c = pipeline.getCursor();
		
		if (_url != null) {
			IDataUtil.put(c, "C8Y_BASEURL", _url);
		} else if (IDataUtil.get(c, "C8Y_BASEURL") == null && System.getenv("C8Y_BASEURL") != null) {
			IDataUtil.put(c, "C8Y_BASEURL", System.getenv("C8Y_BASEURL"));
		}
		
		if (_btenant != null) {
			IDataUtil.put(c, "C8Y_BOOTSTRAP_TENANT", _btenant);
		} else if (IDataUtil.get(c, "C8Y_BOOTSTRAP_TENANT") == null && System.getenv("C8Y_BOOTSTRAP_TENANT") != null) {
			IDataUtil.put(c, "C8Y_BOOTSTRAP_TENANT", System.getenv("C8Y_BOOTSTRAP_TENANT"));
		}
		
		if (_buser != null) {
			IDataUtil.put(c, "C8Y_BOOTSTRAP_USER", _buser);
		} else if (IDataUtil.get(c, "C8Y_BOOTSTRAP_USER") == null && System.getenv("C8Y_BOOTSTRAP_USER") != null) {		
			IDataUtil.put(c, "C8Y_BOOTSTRAP_USER", System.getenv("C8Y_BOOTSTRAP_USER"));
		}
		
		if (_bpassword != null)
			IDataUtil.put(c, "C8Y_BOOTSTRAP_PASSWORD", _bpassword);
		else if (IDataUtil.get(c, "C8Y_BOOTSTRAP_PASSWORD") == null && System.getenv("C8Y_BOOTSTRAP_PASSWORD") != null)
			IDataUtil.put(c, "C8Y_BOOTSTRAP_PASSWORD", System.getenv("C8Y_BOOTSTRAP_PASSWORD"));
		
		// only applicable if agent is in Multi-tenant scope
		// otherwise need to call application/subscriptions to get individual tenants
		
		if (_tenant != null)
			IDataUtil.put(c, "C8Y_TENANT", _tenant);
		else if (IDataUtil.get(c, "C8Y_TENANT") == null && System.getenv("C8Y_TENANT") != null)
			IDataUtil.put(c, "C8Y_TENANT", System.getenv("C8Y_TENANT"));
		else if (IDataUtil.get(c, "C8Y_TENANT") == null && _TEMP_APP_ID != null)
			IDataUtil.put(c, "C8Y_TENANT", _TEMP_APP_ID);
		
		if (_user != null) 
			IDataUtil.put(c, "C8Y_USER", _user);
		else if (IDataUtil.get(c, "C8Y_USER") == null && System.getenv("C8Y_USER") != null)
			IDataUtil.put(c, "C8Y_USER", System.getenv("C8Y_USER"));
		
		if (_password != null)
			IDataUtil.put(c, "C8Y_PASSWORD", _password);
		else if (IDataUtil.get(c, "C8Y_PASSWORD") == null && System.getenv("C8Y_PASSWORD") != null)
			IDataUtil.put(c, "C8Y_PASSWORD", System.getenv("C8Y_PASSWORD"));
		
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getIDFromLocation (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getIDFromLocation)>> ---
		// @sigtype java 3.5
		// [i] field:0:required location
		// [o] field:0:required id
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String location = IDataUtil.getString(pipelineCursor, "location");
		pipelineCursor.destroy();
		
		// process
		
		String id = null;
		
		if (location != null && location.indexOf("/") != -1) {
		
			id = location.substring(location.lastIndexOf("/")+1);
			
		}
		
		// pipeline out
		
		if (id != null)
			IDataUtil.put(pipelineCursor, "id", id);
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getISO861Date (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getISO861Date)>> ---
		// @sigtype java 3.5
		// [o] field:0:required timeStamp
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "timeStamp", currentISO8601TimeString());
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void localName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(localName)>> ---
		// @sigtype java 3.5
		// [o] field:0:required name
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "name", ServerAPI.getServerName());
		// --- <<IS-END>> ---

                
	}



	public static final void setProperties (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setProperties)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional C8Y_BASEURL
		// [i] field:0:optional C8Y_BOOTSTRAP_TENANT
		// [i] field:0:optional C8Y_BOOTSTRAP_USER
		// [i] field:0:optional C8Y_BOOTSTRAP_PASSWORD
		// [i] field:0:optional C8Y_TENANT
		// [i] field:0:optional C8Y_USER
		// [i] field:0:optional C8Y_PASSWORD
		IDataCursor c = pipeline.getCursor();
		_url = IDataUtil.getString(c, "C8Y_BASEURL");
		_btenant = IDataUtil.getString(c, "C8Y_BOOTSTRAP_TENANT");
		_buser = IDataUtil.getString(c, "C8Y_BOOTSTRAP_USER");
		_bpassword = IDataUtil.getString(c, "C8Y_BOOTSTRAP_PASSWORD");
		_tenant = IDataUtil.getString(c, "C8Y_TENANT");
		_bpassword = IDataUtil.getString(c, "C8Y_BOOTSTRAP_PASSWORD");
		_user = IDataUtil.getString(c, "C8Y_USER");
		_password = IDataUtil.getString(c, "C8Y_PASSWORD");
		
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void setTempAppID (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setTempAppID)>> ---
		// @sigtype java 3.5
		// [i] field:0:required appID
		IDataCursor c = pipeline.getCursor();
		_TEMP_APP_ID = IDataUtil.getString(c, "appID");
		c.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void uniqueKey (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(uniqueKey)>> ---
		// @sigtype java 3.5
		// [o] field:0:required key
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "key", ("" + new Date().hashCode()).substring(1));
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static String _TEMP_APP_ID = null;
	
	private static String _url = null;
	private static String _btenant = null;
	private static String _buser = null;
	private static String _bpassword = null;
	private static String _tenant = null;
	private static String _user = null;
	private static String _password = null;
	
	private static String currentISO8601TimeString() {
		
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());
	}
	// --- <<IS-END-SHARED>> ---
}

