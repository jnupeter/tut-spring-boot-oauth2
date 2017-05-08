package com.example.model;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pweicai on 8/05/17.
 */
public class PersonMapper implements ContextMapper<Person> {

    @Override
    public Person mapFromContext(Object ctx) throws NamingException {
        DirContextAdapter context = (DirContextAdapter)ctx;
        Person p = new Person();
        p.setEmail(context.getStringAttribute("mail"));
        p.setFullName(context.getStringAttribute("displayname"));
        //context.get
        //Attributes memberOfAttributes = context.getAttributes("memberOf");
        //LOGGER.info("attributes size:" + memberOfAttributes.size());

        String[] roleDNs = context.getStringAttributes("memberOf");
        Set<String> roleCNs = new HashSet<String>();
        for(String roleDN : roleDNs) {
            roleCNs.add(getRoleCN(roleDN));
        }

        p.setRoles(roleCNs);
        return p;
    }

    /**
     *  Need more elegant implementation for this.
     * @param roleDN
     * @return
     */
    private String getRoleCN(final String roleDN) {
        if(StringUtils.isEmpty(roleDN)) {
            return roleDN;
        } else {
            return roleDN.substring(0, roleDN.indexOf(",")).replace("CN=","");
        }
    }
}