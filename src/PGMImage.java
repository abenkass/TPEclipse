/**
 * 
 * @author centralenantes
 *
 */

import java.io.*;

public class PGMImage {

	int[][] levels;
	public int[][] getLevels() {
		return levels;
	}

	public void setLevels(int[][] levels) {
		this.levels = levels;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	int largeur;
	int hauteur;

	/**
	 * PGMImage default constructor.
	 * @param hauteur
	 * @param largeur
	 */
	public PGMImage(int largeur, int hauteur) {
		levels = new int[largeur][hauteur];
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	/**
	 * PGMImage constructor from file.
	 * @param filePath
	 * 
	 */
	public PGMImage(String filePath) throws IOException{
		getImageFromFilePath(filePath);
	}
	
	public PGMImage(PGMImage image){
		largeur = image.getLargeur();
		hauteur = image.getHauteur();
		levels = new int[largeur][hauteur];
		int[][] imglevels = image.getLevels();
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				levels[i][j] = imglevels[i][j];
			}
		}
	}


	/**
	 * construct array from filePath.
	 * @param filePath : the input file path.
	 */
	private void getImageFromFilePath(String filePath) throws IOException{
		BufferedReader br;
		br = new BufferedReader(new FileReader(filePath));

		String currentLine;

		currentLine = br.readLine();
		if(!currentLine.contains("P2")){
			throw new IOException("'P2' not found");
		}

		currentLine = br.readLine();
		currentLine = br.readLine();
		String[] dimension = currentLine.split(" ");
		
		currentLine = br.readLine();

		int largeur = Integer.parseInt(dimension[0]);
		int hauteur = Integer.parseInt(dimension[1]);
		
		this.largeur = largeur;
		this.hauteur = hauteur;

		levels = new int[largeur][hauteur];
		int i=0;
		int j=0;
		while((currentLine = br.readLine())!=null){
			String[] line = currentLine.split("	");
			for(int k=0;k<line.length;k++){
				if(j>=hauteur){
					throw new IOException("wrong height");
				}
				levels[i][j] = Integer.parseInt(line[i]);
				i++;
				if(i>= largeur){
					j++;
					i=0;
				}
			}
		}
		if(j!=hauteur || i!=0){
			throw new IOException("Wrong dimensions");
		}
	}
	
	/**
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public void saveImageasPGMFile(String filepath) throws IOException{
		
		File file = new File(filepath);
		 
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(this.toString());
		bw.close();
	}

	@Override
	public String toString() {
		String result = "P2"+System.getProperty("line.separator")+largeur+" "+hauteur+System.getProperty("line.separator")+"255";
		String line = "";
		for(int i=0; i<hauteur; i++){
			System.out.println(i);
			for(int j=0; j<largeur; j++){
				String carac = String.valueOf(levels[j][i]);
				if(line.length()+ carac.length() >=70){
					result+= line + System.getProperty("line.separator");
					line = carac;
				}
				else{
					line += "	"+carac;
				}
			}
		}
		return result;	
	}
	
	/**
	 * 
	 * @return PGMImage : histogramme
	 */
	public PGMImage generateHistogram(){
		int[] tableau = new int[255];
		
		int max = 0;
		
		for(int i=0;i<largeur; i++){
			for(int j=0; j<hauteur; j++){
				int last = tableau[levels[i][j]]++;
				if(last > max){
					max = last;
				}
			}
		}
		
		int[][] histo = new int[255][max+1];
		
		for(int i=0;i<255;i++){
			for(int j=0;j<tableau[i];j++){
				histo[i][j] = 255;
			}
		}
		
		PGMImage histogram = new PGMImage(255, max);
		histogram.setLevels(histo);
		
		return histogram;
	}
	
	/**
	 * 
	 * @param seuil
	 */
	public void level(int seuil){
		for(int i=0;i<largeur;i++){
			for(int j=0;j<hauteur;j++){
				if(levels[i][j]>=seuil){
					levels[i][j] = 255;
				}
				else{
					levels[i][j] = 0;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param image
	 * @throws Exception
	 */
	public void difference(PGMImage image) throws Exception{
		if(image.getHauteur() != this.getHauteur() || image.getLargeur()!= this.getLargeur()){
			throw new Exception("Wrong dimensions");
		}
		
		int[][] imgLevels = image.getLevels();
		
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				levels[i][j] = Math.abs(levels[i][j] - imgLevels[i][j]);
			}
		}
	}
	
	
	
}
