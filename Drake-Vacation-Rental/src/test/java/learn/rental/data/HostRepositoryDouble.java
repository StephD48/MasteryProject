package learn.rental.data;


import learn.rental.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class HostRepositoryDouble implements HostRepository{

    public static String HOST_ID = "2e72f86c-b8fe-4265-b4f1-304dea8762db";

    public static Host HOST = new Host(HOST_ID,"de Clerk",
            "kdeclerkdc@sitemeter.com", "(208) 9496329", "2 Debra Way", "Boise", "ID",
            "83757", new BigDecimal(200),new BigDecimal(250));



    private final List<Host> hosts = new ArrayList<>();
    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return findByEmail(email);
    }


}
