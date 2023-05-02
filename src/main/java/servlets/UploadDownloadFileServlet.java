package servlets;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ApiService;
import logic.FileRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static data.DataForConnection.FIELDS;

@WebServlet("/UploadDownloadFileServlet")
public class UploadDownloadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    FileRepository fileRepository;
    ApiService apiService;
    @Override
    public void init() throws ServletException {
        fileRepository = new FileRepository();
        apiService = new ApiService();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String uploadPath = request.getServletContext().getAttribute("FILES_DIR").toString();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String id = request.getParameter("id");
        String fileName = "user"+id+".txt";
        try {
            fileRepository.userToFile(apiService.getUserByUserID(id,FIELDS),uploadPath+File.separator+fileName);
        } catch (ClientException | ApiException e) {
            throw new RuntimeException(e);
        }

        File file = new File(uploadPath+File.separator+fileName);
        if(!file.exists()){
            throw new ServletException("File doesn't exists on server.");
        }
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        ServletOutputStream os       = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read=0;
        while((read = fis.read(bufferData))!= -1){
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}