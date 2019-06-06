package com.loja.jesus.likeseries;

import android.content.Context;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarMensaje extends javax.mail.Authenticator {
    private static final String correo = "jesus.rodriguez240498@gmail.com";
    private static final String contraseña = "Ventanadelsalon24";
    private Context contexto;
    private Session sesion;

    public EnviarMensaje(Context contexto) {
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.socketFactory.port", "465");
        propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.port", "465");
        propiedades.put("mail.smtp.socketFactory.fallback", "false");
        propiedades.setProperty("mail.smtp.quitwait", "false");
        propiedades.setProperty("mail.transport.protocol", "smtp");
        propiedades.setProperty("mail.host", "smtp.gmail.com");
        sesion = Session.getDefaultInstance(propiedades, this);
        this.contexto = contexto;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(correo,contraseña);
    }

    public boolean enviarEmail(String pelioserietertulia,String nombre,String correo,String nombrecontenido) {

        boolean enviado = false;
        if(pelioserietertulia.equals("peli")) {
            try {
                Message mensajecorreo = new MimeMessage(sesion);
                mensajecorreo.setFrom(new InternetAddress(correo));
                mensajecorreo.setSubject("El equipo de LikeSeries informa, se ha agregado una nueva película");
                mensajecorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
                mensajecorreo.setContent("Hola de nuevo " + " <b>" + nombre + "</b> " + ", te informamos que nuestro administrador a insertado una nueva película , con el nombre " + "<b>" + nombrecontenido + "<b/>." + "\n¡Se el primero en votarla!", "text/html; charset=utf-8");
                Transport.send(mensajecorreo);
                enviado = true;
            } catch (Exception e) {
                e.toString();
            }

        }
        else if(pelioserietertulia.equals("serie"))
        {
            try {
                Message mensajecorreo = new MimeMessage(sesion);
                mensajecorreo.setFrom(new InternetAddress(correo));
                mensajecorreo.setSubject("El equipo de LikeSeries informa, se ha agregado una nueva serie");
                mensajecorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
                mensajecorreo.setContent("Hola de nuevo " + " <b>" + nombre + "</b> " + ", te informamos que nuestro administrador a insertado una nueva serie , con el nombre " + "<b>" + nombrecontenido + "<b/>." + "\n¡Se el primero en votarla!", "text/html; charset=utf-8");
                Transport.send(mensajecorreo);
                enviado = true;
            } catch (Exception e) {
                e.toString();
            }
        }
        else if(pelioserietertulia.equals("tertulia"))
        {
            try {
                Message mensajecorreo = new MimeMessage(sesion);
                mensajecorreo.setFrom(new InternetAddress(correo));
                mensajecorreo.setSubject("El equipo de LikeSeries informa, se ha agregado una nueva tertulia");
                mensajecorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
                mensajecorreo.setContent("Hola de nuevo " + " <b>" + nombre + "</b> " + ", te informamos que nuestro administrador a insertado una nueva tertulia , con el nombre " + "<b>" + nombrecontenido +"."+ "<b/>" + "\n¡Ve ha echarle un vistazo!", "text/html; charset=utf-8");
                Transport.send(mensajecorreo);
                enviado = true;
            } catch (Exception e) {
                e.toString();
            }
        }
        return enviado;
}
}
