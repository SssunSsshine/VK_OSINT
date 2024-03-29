package servlets.download;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;

@WebListener
public class FileLocationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String rootPath = System.getProperty("catalina.home");
        ServletContext ctx = servletContextEvent.getServletContext();
        String relativePath = ctx.getInitParameter("tempfile.dir");
        File file = new File(rootPath + File.separator + relativePath);
        if(!file.exists()) file.mkdirs();
        System.out.println("File Directory created to be used for storing files");
        ctx.setAttribute("FILES_DIR_FILE", file);
        ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
    }
}