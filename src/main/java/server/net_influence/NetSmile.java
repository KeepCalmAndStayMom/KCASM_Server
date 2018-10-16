package server.net_influence;

import java.util.Map;

public interface NetSmile {

    void initialSetting();

    void runNet();

    void clearNet();

    void setAllEvidence(Map<String, Object> evidences);

    String getResultUtility();
}
