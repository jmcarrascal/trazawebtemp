package ar.com.cipres.services.impl.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.FormatUtil;

public class ReadingEmail {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "jmcarrascal@gmail.com", "minuevavida");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm sender = new FromTerm(new InternetAddress("noReply@oca.com.ar"));            
            SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, DateUtil.getDate("30-09-2016"));
            SearchTerm andTerm = new AndTerm(sender, newerThan);
            Message[] messages = inbox.search(andTerm);
            List<File> attachments = new ArrayList<File>();
            for (int i = 1; i < messages.length; i++) {
            	 System.out.println("SENT DATE:" + messages[i].getSentDate());
                 System.out.println("SUBJECT:" + messages[i].getSubject());
                 Multipart multipart = (Multipart) messages[i].getContent();
                 // System.out.println(multipart.getCount());

                 for (int j = 0; j < multipart.getCount(); j++) {
                     BodyPart bodyPart = multipart.getBodyPart(j);
                     if(j == 0) {
                         continue; // dealing with attachments only
                     } 
                     
                     InputStream is = bodyPart.getInputStream();
                     BufferedReader in = new BufferedReader(new InputStreamReader(is));
                     String line;
                     while ((line = in.readLine()) != null) {
                         System.out.println(line);
                         System.out.println(FormatUtil.llenoConCeros(line.substring(19, 27).trim(), 8));
                         //System.out.println(line.substring(62, 70));
                     }
                     
                     File f = new File("/tmp/" + bodyPart.getFileName());
                     FileOutputStream fos = new FileOutputStream(f);
                     byte[] buf = new byte[4096];
                     int bytesRead;
                     while((bytesRead = is.read(buf))!=-1) {
                         fos.write(buf, 0, bytesRead);
                     }
                     fos.close();
                     attachments.add(f);
                 }
                 
            }
            
          
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}