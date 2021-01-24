package jc.cumulocity.tools.inventory;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void buildQuery (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(buildQuery)>> ---
		// @sigtype java 3.5
		// [i] recref:0:required queries jc.cumulocity.tools.inventory.docs:QueryGroup
		// [o] field:0:required queryString
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		IData queries = IDataUtil.getIData(pipelineCursor, "queries");
		
		String queryString;
		try {
			queryString = new QueryCollection(queries).build();
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException(e);
		}
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "queryString", queryString);
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	static class QueryCollection {
		
		public Query[] queries;
		public boolean matchAll = true;
		
		public QueryCollection(IData data) {
			
			IDataCursor c = data.getCursor();
			
			try {
				this.matchAll = (boolean) IDataUtil.get(c, "matchAll");
			} catch(Exception e) {
				// do now't
			}
			
			IData[] queryIDataArray = IDataUtil.getIDataArray(c, "queries");
			
			this.queries = new Query[queryIDataArray.length];
			int i = 0;
			
			for(IData q : queryIDataArray) {
				this.queries[i++] = new Query(q);
			}
			
			c.destroy();
		}
		
		public String build() throws UnsupportedEncodingException {
	
			String b = "";
			Query lastq = null;
			
	        for (Query query : this.queries) {
			
	        	 if (b.isEmpty()) {
		                b += query.toString();
		            } else {
						if (query.exclusive) {
							// need to add parenthesis around this and last query
							
							if (lastq != null && lastq.exclusive) {
								int i = b.lastIndexOf("and");
								b = b.substring(0, i+4) + "(" + b.substring(i+3);
							} else {
								b = "(" + b;
							}
							
							b += " or ";
						} else {
							
							if (lastq != null && lastq.exclusive && !lastq.equals(this.queries[0])) {
								b += ") ";
							}
							
							b += this.matchAll ? " and " : " or ";
						}
						
		                b += query.toString();
		            }
					
					lastq = query;
			}
			
			if (lastq != null && lastq.exclusive && this.queries.length > 1 && !lastq.equals(this.queries[0])) {
				b += ")";
			}
			
			//return b;
			return URLEncoder.encode(b, StandardCharsets.UTF_8.toString());			
	    }
	}
	
	static class Query {
		
		public String lh;
		public Operator op;
		public String rh;
		public boolean exclusive = false;
				
		enum Operator {
			eq,
			ne,
			lt,
			gt,
			has
		}
		
		public Query(String lh, Operator op, String rh) {
			this.lh = lh;
			this.op = op;
			this.rh = rh;
		}
		
		public Query(String lh) {
			this.lh = lh;
			this.op = Operator.has;
		}
		
		public Query(IData data) {
		
			IDataCursor c = data.getCursor();
			
			this.lh = IDataUtil.getString(c, "lh");
			this.op = Operator.valueOf(IDataUtil.getString(c, "op"));
			this.rh = IDataUtil.getString(c, "rh");
			
			try { this.exclusive = (boolean) IDataUtil.get(c, "exclusive"); } catch(Exception e) {}
	
			c.destroy();
		}
		
		public String toString() {
			
			if (this.op != Operator.has) {
	            return String.format("%s %s '%s'", lh, op.toString(), rh);
	        } else { // if no operator, assume key is function
	            return String.format("%s(%s)", op, rh);
	        }
		}
	}
	// --- <<IS-END-SHARED>> ---
}

