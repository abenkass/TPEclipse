/**
 * 
 * @author centralenantes
 *
 */

public class TpEclipse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String path = args[0];
		PGMImage lena;
		
		String pathFolder = path.substring(0, path.lastIndexOf("/")+1);
		
		try{
			lena = new PGMImage(path);
			//lena.saveImageasPGMFile(path2 + "lenaNew.pgm");
			//PGMImage histo = lena.generateHistogram();
			//histo.saveImageasPGMFile(path2 + "lenaHisto.pgm");
			PGMImage lenaoriginale = new PGMImage(lena);
			lena.level(142);
			lena.difference(lenaoriginale);
			lena.saveImageasPGMFile(pathFolder+"lenaDiff.pgm");
			
		}
		catch(Exception e){
			System.out.println("Error received : " + e.getMessage());
			return;
		}
		
	}

}
