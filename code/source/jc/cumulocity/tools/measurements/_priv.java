package jc.cumulocity.tools.measurements;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void constructFilterArgs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(constructFilterArgs)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required from
		// [i] field:0:required to
		// [i] field:0:optional pageSize
		// [i] field:0:optional currentPage
		// [o] field:0:required queryString
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String source = IDataUtil.getString(pipelineCursor, "source");
		String from = IDataUtil.getString(pipelineCursor, "from");
		String to = IDataUtil.getString(pipelineCursor, "to");
		String pageSize = IDataUtil.getString(pipelineCursor, "pageSize");
		String currentPage = IDataUtil.getString(pipelineCursor, "currentPage");
		
		// process
		
		String queryString = "";
		
		if (source != null || from != null || to != null || pageSize != null || currentPage != null)
			queryString = "?";
		
		if (source != null)
			queryString += "source=" + source;
		
		if (from != null) {
			if (queryString.equals("?"))
				queryString += "dateFrom=" + from;
			else
				queryString += "&dateFrom=" + from;
		}
		
		if (to != null) {
			if (queryString.equals("?"))
				queryString += "dateTo=" + to;
			else
				queryString += "&dateTo=" + to;
		}
		
		if (pageSize != null) {
			if (pageSize.equals("?"))
				pageSize += "pageSize=" + pageSize;
			else
				pageSize += "&pageSize=" + pageSize;
		}
		
		if (currentPage != null) {
			if (queryString.equals("?"))
				queryString += "currentPage=" + currentPage;
			else
				queryString += "&currentPage=" + currentPage;
		}
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "queryString", queryString);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void packageMeasurement (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(packageMeasurement)>> ---
		// @sigtype java 3.5
		// [i] field:0:required c8yIdOfDevice
		// [i] recref:0:required measurement jc.cumulocity.tools.measurements.docs:Measurement
		// [o] record:0:required c8y_measurement
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String deviceID = IDataUtil.getString(pipelineCursor, "c8yIdOfDevice");
		IData measurement = IDataUtil.getIData(pipelineCursor, "measurement");
		
		// process
		
		if ( measurement != null)
		{
			try {
				IDataUtil.put(pipelineCursor, "c8y_measurement", new MeasurementsWrapper(measurement).toC8yMeasurement(deviceID));
			} catch(Exception e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
		} 
		
		// pipeline out
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static class MeasurementsWrapper {
		
		private Date _time;
		private Measurement[] _measurements;
		private String _type;
		
		public MeasurementsWrapper(IData doc) {
			
			IDataCursor measurementCursor = doc.getCursor();
			String time = IDataUtil.getString(measurementCursor, "time");
					
			if (time != null)
				this._time = ISO8601TimeStringToDate(time);
			else
				this._time = new Date();
			
			this._type = IDataUtil.getString( measurementCursor, "type");
	
			IData[]	measurements = IDataUtil.getIDataArray(measurementCursor, "measurements");
	
			if (measurements != null)
			{
				this._measurements = new Measurement[measurements.length];
				
				for ( int i = 0; i < measurements.length; i++ )
				{
					this._measurements[i] = new Measurement(measurements[i]);
				}
				
				if (this._type == null)
					this._type = this._measurements[0]._type;
			}
			
			measurementCursor.destroy();
		}
		
		public IData toC8yMeasurement(String deviceID) {
			
			IData doc = IDataFactory.create();
	
			for (Measurement m : this._measurements) {
				m.toC8yMeasurement(doc);
			}
			
			IDataCursor c = doc.getCursor();
			IDataUtil.put(c, "time", dateToISO8601TimeString(this._time));
			IDataUtil.put(c, "source", deviceIdentifier(deviceID));
			IDataUtil.put(c, "type", this._type);
			c.destroy();
			
			return doc;
		}
		
		private IData deviceIdentifier(String deviceID) {
			
			IData doc = IDataFactory.create();
			IDataCursor c = doc.getCursor();
			IDataUtil.put(c, "id", deviceID);
			c.destroy();
			
			return doc;
		}
		
		private Date ISO8601TimeStringToDate(String timeStamp) {
			
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(timeStamp);
		    } catch (ParseException e) {
		        throw new RuntimeException("Not a valid ISO8601: " + timeStamp, e);
		    }
		}
		
		private String dateToISO8601TimeString(Date date) {
			
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(date);
		}
	}
	
	private static class Measurement {
		
		private Values[] _values;
		private String _type;
		
		public Measurement(IData doc) {
			
			IDataCursor measurementCursor = doc.getCursor();
			this._type = IDataUtil.getString( measurementCursor, "type");
	
			IData[]	values = IDataUtil.getIDataArray(measurementCursor, "values");
	
			if (values != null)
			{
				this._values = new Values[values.length];
				
				for ( int i = 0; i < values.length; i++ ) {
					this._values[i] = new Values(values[i]);
				}
			}
			
			measurementCursor.destroy();
		}
		
		public IData toC8yMeasurement(IData doc) {
			
			if (doc == null)
				doc = IDataFactory.create();
			
			IData m = IDataFactory.create();
			int i = this._values.length > 0 ? 0 : -9999;
			for (Values v : this._values) {
				v.toC8yMeasurement(m, i++, this._type);
			}
			
			IDataCursor c = doc.getCursor();
			IDataUtil.put(c, _type, m);
			c.destroy();
			
			return doc;
		}
	}
	
	private static class Values {
		
		private Object _value;
		private String _uom;
		private String _label;
		
		public Values(IData value) {
			
			IDataCursor valuesCursor = value.getCursor();
			_value = IDataUtil.get(valuesCursor, "value");
			_uom = IDataUtil.getString(valuesCursor, "uom");
			_label = IDataUtil.getString(valuesCursor, "label");
			valuesCursor.destroy();
		}
		
		public IData toC8yMeasurement(IData doc, int seq, String type) {
			
			IData u = IDataFactory.create();
			IDataCursor c = u.getCursor();
			
			if (type != null && this._value instanceof String) {
				IDataUtil.put(c, "value", this.value(type));
			} else {
				IDataUtil.put(c, "value", this._value);
			}
			
			IDataUtil.put(c, "unit", this._uom);
			c.destroy();
			
			if (doc == null)
				doc = IDataFactory.create();
			
			c = doc.getCursor();
			IDataUtil.put(c, unitLabel(seq), u);
			c.destroy();
			
			return doc;
		}
		
		private Object value(String type) {
			
			switch (type) {
			case "c8y_TemperatureMeasurement":
				return Float.parseFloat((String) this._value);
			default:
				return Double.parseDouble((String) this._value);
			}
		}
	
		private String unitLabel(int seq) {
			
			if (this._label != null)
				return this._label;
			else if (seq >= 0)
				return "V:" + seq;
			else
				return "V";
		}
	}
		
	// --- <<IS-END-SHARED>> ---
}

