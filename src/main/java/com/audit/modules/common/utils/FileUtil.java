package com.audit.modules.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author : jiadu
 * @Description : 文件处理工具类
 * @date : 2017/3/9
 * Copyright (c) , IsoftStone All Right reserved.
 */
public class FileUtil
{
    public static void writeFile(String fileName, String content)
    {
        writeFile(fileName, content, "utf-8");
    }

    public static void writeFile(String fileName, String content, String charset)
    {
        try
        {
            createFolder(fileName, true);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), charset));

            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String fileName, InputStream is)
            throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        byte[] bs = new byte[512];
        int n = 0;
        while ((n = is.read(bs)) != -1) {
            fos.write(bs, 0, n);
        }
        is.close();
        fos.close();
    }

    public static String readFile(String fileName)
    {
        try
        {
            File file = new File(fileName);
            String charset = getCharset(file);
            StringBuffer sb = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            in.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }return "";
    }

    public static boolean isExistFile(String dir)
    {
        boolean isExist = false;
        File fileDir = new File(dir);
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if ((files != null) && (files.length != 0)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public static String getCharset(File file)
    {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if ((first3Bytes[0] == -1) && (first3Bytes[1] == -2)) {
                charset = "UTF-16LE";
                checked = true;
            } else if ((first3Bytes[0] == -2) && (first3Bytes[1] == -1))
            {
                charset = "UTF-16BE";
                checked = true;
            } else if ((first3Bytes[0] == -17) && (first3Bytes[1] == -69) && (first3Bytes[2] == -65))
            {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();

            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read < 240)
                    {
                        if ((128 > read) || (read > 191))
                        {
                            if ((192 <= read) && (read <= 223)) {
                                read = bis.read();
                                if ((128 > read) || (read > 191))
                                {
                                    break;
                                }

                            }
                            else if ((224 <= read) && (read <= 239)) {
                                read = bis.read();
                                if ((128 <= read) && (read <= 191)) {
                                    read = bis.read();
                                    if ((128 <= read) && (read <= 191)) {
                                        charset = "UTF-8";
                                    }
                                }
                            }
                        }
                    }
                }

            }

            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    public static byte[] readByte(InputStream is)
    {
        try
        {
            byte[] r = new byte[is.available()];
            is.read(r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readByte(String fileName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            byte[] r = new byte[fis.available()];
            fis.read(r);
            fis.close();
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeByte(String fileName, byte[] b)
    {
        try
        {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(fileName));

            fos.write(b);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDir(File dir)
    {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void serializeToFile(Object obj, String fileName)
    {
        try
        {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));

            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserializeFromFile(String fileName)
    {
        try
        {
            File file = new File(fileName);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

            Object obj = in.readObject();
            in.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }return null;
    }

    public static String inputStream2String(InputStream input, String charset)
            throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(input, charset));

        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line + "\n");
        }
        return buffer.toString();
    }

    public static String inputStream2String(InputStream input)
            throws IOException
    {
        return inputStream2String(input, "utf-8");
    }

    public static File[] getFiles(String path)
    {
        File file = new File(path);
        return file.listFiles();
    }

    public static void createFolderFile(String path)
    {
        createFolder(path, true);
    }

    public static void createFolder(String path, boolean isFile)
    {
        if (isFile) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }

    public static void renameFolder(String path, String newName)
    {
        File file = new File(path);
        if (file.exists())
            file.renameTo(new File(newName));
    }

    public static ArrayList<File> getDiretoryOnly(File dir)
    {
        ArrayList dirs = new ArrayList();
        if ((dir != null) && (dir.exists()) && (dir.isDirectory())) {
            File[] files = dir.listFiles(new FileFilter()
            {
                public boolean accept(File file)
                {
                    if (file.isDirectory())
                        return true;
                    return false;
                }
            });
            for (int i = 0; i < files.length; i++) {
                dirs.add(files[i]);
            }
        }
        return dirs;
    }

    public ArrayList<File> getFileOnly(File dir)
    {
        ArrayList dirs = new ArrayList();
        File[] files = dir.listFiles(new FileFilter()
        {
            public boolean accept(File file) {
                if (file.isFile())
                    return true;
                return false;
            }
        });
        for (int i = 0; i < files.length; i++) {
            dirs.add(files[i]);
        }
        return dirs;
    }

    public static boolean deleteFile(String path)
    {
        File file = new File(path);
        return file.delete();
    }

    public static boolean copyFile(String from, String to)
    {
        File fromFile = new File(from);
        File toFile = new File(to);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try
        {
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);

            byte[] buf = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }

            fos.flush();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void backupFile(String filePath)
    {
        String backupName = filePath + ".bak";
        File file = new File(backupName);
        if (file.exists()) {
            file.delete();
        }
        copyFile(filePath, backupName);
    }

    public static String getFileExt(File file)
    {
        if (file.isFile()) {
            return getFileExt(file.getName());
        }
        return "";
    }

    public static String getFileExt(String fileName)
    {
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) {
            return fileName.substring(pos + 1).toLowerCase();
        }
        return "";
    }

    public static void copyDir(String fromDir, String toDir)
            throws IOException
    {
        new File(toDir).mkdirs();
        File[] file = new File(fromDir).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                String fromFile = file[i].getAbsolutePath();
                String toFile = toDir + "/" + file[i].getName();

                copyFile(fromFile, toFile);
            }
            if (file[i].isDirectory())
                copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
        }
    }

    private static void copyDirectiory(String fromDir, String toDir)
            throws IOException
    {
        new File(toDir).mkdirs();
        File[] file = new File(fromDir).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                String fromName = file[i].getAbsolutePath();
                String toFile = toDir + "/" + file[i].getName();
                copyFile(fromName, toFile);
            }
            if (file[i].isDirectory())
                copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
        }
    }

    public static String getFileSize(File file)
            throws IOException
    {
        if (file.isFile()) {
            FileInputStream fis = new FileInputStream(file);
            int size = fis.available();
            fis.close();
            return getSize(size);
        }
        return "";
    }

    public static String getSize(double size)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        if (size > 1048576.0D) {
            double ss = size / 1048576.0D;
            return df.format(ss) + " M";
        }if (size > 1024.0D) {
        double ss = size / 1024.0D;
        return df.format(ss) + " KB";
    }
        return size + " bytes";
    }

    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fullPath, String fileName)
            throws IOException
    {
        OutputStream outp = response.getOutputStream();
        File file = new File(fullPath);
        if (file.exists()) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            String filedisplay = fileName;
            String agent = request.getHeader("USER-AGENT");

            if (agent.toUpperCase().indexOf("MSIE") > 0)
                filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            else {
                filedisplay = new String(filedisplay.getBytes("UTF-8"), "ISO8859-1");
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);

            FileInputStream in = null;
            try {
                outp = response.getOutputStream();
                in = new FileInputStream(fullPath);
                byte[] b = new byte[1024];
                int i = 0;
                while ((i = in.read(b)) > 0) {
                    outp.write(b, 0, i);
                }
                outp.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (outp != null) {
                    outp.close();
                    outp = null;
                    response.flushBuffer();
                }
            }
        } else {
            outp.write("文件不存在!".getBytes("utf-8"));
        }
    }

    public static void downLoad(HttpServletRequest request, HttpServletResponse response, String content, String fileName)
            throws IOException
    {
        response.setContentType("APPLICATION/OCTET-STREAM");
        String filedisplay = fileName;
        String agent = request.getHeader("USER-AGENT");

        if (agent.toUpperCase().indexOf("MSIE") > 0)
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
        else {
            filedisplay = new String(filedisplay.getBytes("UTF-8"), "ISO8859-1");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + filedisplay);
        PrintWriter out = response.getWriter();
        out.write(content);
        out.flush();
        out.close();
    }

    public static String getParentDir(String baseDir, String currentFile)
    {
        File f = new File(currentFile);
        String parentPath = f.getParent();
        String path = parentPath.replace(baseDir, "");
        return path.replace(File.separator, "/");
    }

    public static String readFromProperties(String fileName, String key)
    {
        String value = "";
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(fileName));
            Properties prop = new Properties();
            prop.load(stream);
            value = prop.getProperty(key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static boolean saveProperties(String fileName, String key, String value)
    {
        StringBuffer sb = new StringBuffer();
        boolean isFound = false;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.startsWith(key)) {
                    sb.append(key + "=" + value + "\r\n");
                    isFound = true;
                }
                else {
                    sb.append(str + "\r\n");
                }
            }

            if (!isFound) {
                sb.append(key + "=" + value + "\r\n");
            }
            writeFile(fileName, sb.toString(), "utf-8");
            return true;
        }
        catch (Exception ex)
        {
            boolean bool1;
            ex.printStackTrace();
            return false;
        }
        finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static boolean delProperties(String fileName, String key)
    {
        StringBuffer sb = new StringBuffer();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (!str.startsWith(key)) {
                    sb.append(str + "\r\n");
                }
            }
            writeFile(fileName, sb.toString(), "utf-8");
            return true;
        }
        catch (Exception ex)
        {
            boolean bool;
            ex.printStackTrace();
            return false;
        }
        finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static List<Class<?>> getAllClassesByInterface(Class<?> interfaceClass, boolean samePackage)
            throws IOException, ClassNotFoundException, IllegalStateException
    {
        if (!interfaceClass.isInterface()) {
            throw new IllegalStateException("Class not a interface.");
        }

        ClassLoader $loader = interfaceClass.getClassLoader();

        String packageName = samePackage ? interfaceClass.getPackage().getName() : "/";
        return findClasses(interfaceClass, $loader, packageName);
    }

    private static List<Class<?>> findClasses(Class<?> interfaceClass, ClassLoader loader, String packageName)
            throws IOException, ClassNotFoundException
    {
        List allClasses = new ArrayList();

        String packagePath = packageName.replace(".", "/");
        if (!packagePath.equals("/")) {
            Enumeration resources = loader.getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL $url = (URL)resources.nextElement();
                allClasses.addAll(findResources(interfaceClass, new File($url.getFile()), packageName));
            }
        } else {
            String path = loader.getResource("").getPath();
            allClasses.addAll(findResources(interfaceClass, new File(path), packageName));
        }
        return allClasses;
    }

    private static List<Class<?>> findResources(Class<?> interfaceClass, File directory, String packageName)
            throws ClassNotFoundException
    {
        List $results = new ArrayList();
        if (!directory.exists()) return Collections.EMPTY_LIST;
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory())
            {
                if (!file.getName().contains(".")) {
                    if (!packageName.equals("/"))
                        $results.addAll(findResources(interfaceClass, file, packageName + "." + file.getName()));
                    else
                        $results.addAll(findResources(interfaceClass, file, file.getName()));
                }
            }
            else if (file.getName().endsWith(".class")) {
                Class clazz = null;
                if (!packageName.equals("/"))
                    clazz = Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
                else {
                    clazz = Class.forName(file.getName().substring(0, file.getName().length() - 6));
                }
                if ((interfaceClass.isAssignableFrom(clazz)) && (!interfaceClass.equals(clazz))) {
                    $results.add(clazz);
                }
            }
        }
        return $results;
    }

    public static Object cloneObject(Object obj)
            throws Exception
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);

        return in.readObject();
    }

    public static void downLoadFileByByte(HttpServletRequest request, HttpServletResponse response, byte[] b, String fileName)
            throws IOException
    {
        OutputStream outp = response.getOutputStream();
        if (b.length > 0) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            String filedisplay = fileName;
            String agent = request.getHeader("USER-AGENT");

            if ((agent != null) && (agent.indexOf("MSIE") == -1)) {
                String enableFileName = "=?UTF-8?B?" + new String(getBase64(filedisplay)) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            }
            else {
                filedisplay = URLEncoder.encode(filedisplay, "utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            }
            outp.write(b);
        } else {
            outp.write("文件不存在!".getBytes("utf-8"));
        }
        if (outp != null) {
            outp.close();
            outp = null;
            response.flushBuffer();
        }
    }
    public static String getBase64(String s)
            throws UnsupportedEncodingException {
        byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(s.getBytes("utf-8"));
        return new String(bytes, "utf-8");
    }
}