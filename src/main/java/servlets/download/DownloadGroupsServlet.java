package servlets.download;

import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.repo.ApiRepo;
import logic.repo.FileRepository;
import logic.service.CastService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@WebServlet("/download-groups")
public class DownloadGroupsServlet extends HttpServlet {
    FileRepository fileRepository;
    ApiRepo apiService;

    @Override
    public void init() throws ServletException {
        fileRepository = new FileRepository();
        apiService = new ApiRepo();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        HttpSession session = request.getSession();
        GetResponse user = (GetResponse) session.getAttribute("user");
        Map<String, List<Integer>> mutualGroups = CastService.castMap(session.getAttribute("mutualGroups"), String.class, Integer.class);
        List<GetByIdObjectLegacyResponse> groups = CastService.castList(session.getAttribute("groups"), GetByIdObjectLegacyResponse.class);

        String uploadPath = request.getServletContext().getAttribute("FILES_DIR").toString();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String id = request.getParameter("id");
        String fileName = "user" + id + "groups" + ".txt";

        fileRepository.groupsToFile(user, groups, mutualGroups, uploadPath + File.separator + fileName);

        File file = new File(uploadPath + File.separator + fileName);
        if (!file.exists()) {
            throw new ServletException("File doesn't exists on server.");
        }
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        ServletOutputStream os = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read = 0;
        while ((read = fis.read(bufferData)) != -1) {
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
