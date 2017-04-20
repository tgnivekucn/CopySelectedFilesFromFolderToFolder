import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CopyFilesToDst {
	
	private String dstPath = "";

	private String mSrcDirPath = "";
	private ArrayList<String> specifiedFiles = new ArrayList<>(); 
	private String mDstDirPath = "";
	private String mOriginalSrcDirPath = "";
	private String mDstDirName = "DstFolder";
	CopyFilesToDst(final String originalSrcDirPath, final String dstDirName, final String srcDirPath, final String dstDirPath){
		mOriginalSrcDirPath = originalSrcDirPath;
		mDstDirName = "DstFolder"+"\\"+dstDirName;
		mSrcDirPath = srcDirPath;
		mDstDirPath = dstDirPath;
	}
	
	public void startCopy(){
		specifiedFiles.clear();

		try {
			getCopyFileNameList(mSrcDirPath);
			copy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getCopyFileNameList(final String srcDirPath) throws IOException{
		File folder = new File(mSrcDirPath);

	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	            specifiedFiles.add(fileEntry.getName());
	        }
	    }
	}
	
	private void copy(){
		
		//1. if dst dir is not existed, then create it
		String parentDirPath = mDstDirPath+"\\DstFolder";
		String sonDirPath = mDstDirPath+"\\"+mDstDirName;

		File tmpDir = new File(parentDirPath);
        // if file doesnt exists, then create it
        if (!tmpDir.exists()) {
        	System.out.println("make dir, dir path is: " + parentDirPath);
        	tmpDir.mkdir();
        }
        tmpDir = new File(sonDirPath);
        // if file doesnt exists, then create it
        if (!tmpDir.exists()) {
        	System.out.println("make dir, dir path is: " + sonDirPath);
        	tmpDir.mkdir();
        }
        
        
        
        
        //2. start copy file to dst dir
		for(String filename: specifiedFiles){
			final String srcPath = mOriginalSrcDirPath+"\\"+filename;
			final String dstPath = mDstDirPath+"\\"+mDstDirName+"\\"+filename;
			File tmp = new File(dstPath);
			if(tmp.exists()){
				tmp.delete();
			}
			
	        try {
				copyFileUsingJava7Files(srcPath, 
						dstPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	private void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	
	private static void copyFileUsingJava7Files(final String srcPathString, final String dstPathString)
			throws IOException {
        System.out.println("srcPathString is: " + srcPathString);
        System.out.println("dstPathString is: " + dstPathString);

		Path dstPath = Paths.get(dstPathString);
		Path srcPath = Paths.get(srcPathString);

		Files.copy(srcPath, dstPath);
	}

	
}
