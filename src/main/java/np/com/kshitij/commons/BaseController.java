package np.com.kshitij.commons;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
public abstract class BaseController extends BaseComponent {
    protected static Map<String, String[]> getQueryParams(HttpServletRequest request) {
        Map<String, String[]> qp = new HashMap<>();
        String qs = request.getQueryString();
        if (StringUtils.isNotBlank(qs)) {
            String[] split = qs.split("&");
            for (String s : split) {
                String key = s.split("=")[0];
                qp.put(key, request.getParameterValues(key));
            }
        }
        return qp;
    }

    protected PageRequest pageRequest(HttpServletRequest request) {
        String token = request.getParameter("t");
        if (StringUtils.isNotBlank(token)) {
            String json = Base64Encoder.decode(token);
            return parse(json, PageRequest.class);
        } else {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setFrom(getFrom(request));
            pageRequest.setSize(getParam(request, "s", 10));
            pageRequest.setOrder(Order.forName(getParam(request, "o", "DESC")));
            pageRequest.setParams(getQueryParams(request));
            return pageRequest;
        }
    }

    private OffsetDateTime getFrom(HttpServletRequest request) {
        String from = getParam(request, "from", "");
        OffsetDateTime offsetDateTime;
        if (StringUtils.isBlank(from)) {
            offsetDateTime = OffsetDateTime.now();
        } else {
            offsetDateTime = OffsetDateTime.parse(from);
        }
        return offsetDateTime;
    }

    protected String getParam(HttpServletRequest request, String name, String defaultValue) {
        String param = request.getParameter(name);
        return param == null ? defaultValue : param;
    }

    protected int getParam(HttpServletRequest request, String name, int defaultValue) {
        String from = request.getParameter(name);
        if (StringUtils.isNotBlank(from)) {
            try {
                return Integer.parseInt(from);
            } catch (NumberFormatException e) {
                //do nothing
            }
        }
        return defaultValue;
    }

    protected long getParam(HttpServletRequest request, String name, long defaultValue) {
        String from = request.getParameter(name);
        if (StringUtils.isNotBlank(from)) {
            try {
                return Long.parseLong(from);
            } catch (NumberFormatException e) {
                //do nothing
            }
        }
        return defaultValue;
    }
}
