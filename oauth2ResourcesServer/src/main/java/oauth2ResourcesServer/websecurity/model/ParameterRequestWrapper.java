package oauth2ResourcesServer.websecurity.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String,String[]> params=new HashMap<>();

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
    }

    public ParameterRequestWrapper(HttpServletRequest request,Map<String,Object>extendParams){
        this(request);
        addAllParameters(extendParams);
    }

    @Override
    public Enumeration<String> getParameterNames(){
        return new Vector(params.keySet()).elements();
    }

    @Override
    public String getParameter(String name){
        String[] values=params.get(name);
        if(values==null || values.length==0){
            return null;
        }
        return values[0];
    }


    @Override
    public String[] getParameterValues(String name){
        String[] values=params.get(name);
        if(values==null || values.length==0){
            return null;
        }
        return values;
    }

    public void addAllParameters(Map<String,Object> otherPamams){
        for(Map.Entry<String,Object> entry:otherPamams.entrySet()){
            addParameter(entry.getKey(),entry.getValue());
        }
    }

    public void addParameter(String name,Object value){
        if(value!=null){
            if(value instanceof String[]){
                params.put(name,(String[])value);
            }else if(value instanceof String){
                params.put(name,new String[]{(String) value});
            }else{
                params.put(name,new String[]{String.valueOf(value)});
            }
        }
    }

}
