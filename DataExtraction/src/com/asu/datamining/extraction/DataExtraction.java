package com.asu.datamining.extraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


class Extraction {

	ArrayList<String> ar;

	public void populateArrayList()
	{
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("Files/SatyaPrajaktaURLs.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				ar.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public Extraction()
	{
		ar = new ArrayList<String>();
	}
	public void extractFromURL()
	{
		//ar.clear();ar.add("http://www.snopes.com/rumors/atonement.asp");
		try {
			for(String url : ar)
			{
				String url1 = url;
				url1=url1.replaceAll("http://www.snopes.com/", "");
				url1=url1.replaceAll("(asp$|php$)", "txt");
				url1 = url1.replaceAll("/","-");
				url1= "Files/output/"+url1;
				System.out.println(url1);
				File file = new File(url1);
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
					
				}
				
				
				
				Document doc = Jsoup.connect(url).get();
				//Elements exampleLinks = doc.select("div[style^=text-align: justify; margin-top: 10px; margin-bottom: 10px; margin-left: 15px; margin-right: 15px]");
				Elements exampleLinks = doc.select("div[style^=text-align: justify; margin-left: 15px;  margin-right: 15px]");
				if(exampleLinks.isEmpty())
					exampleLinks = doc.select("div[class^=article_text");
				
				
				for(Element link : exampleLinks)
				{
					String Claim = "";
					String Status = "";
					String Example = "";
					String Origins = "";
					String LastUpdated = "";
					String Item="";
					String linkString=link.toString();
					String plainText = Jsoup.parse(linkString).text();
					int ClaimIndex = plainText.indexOf("Claim:");
					int StatusIndex = plainText.indexOf("Status:");

					String ItemOrClaim = "Claim:";
					int itemIndex = plainText.indexOf("Item:");
					
					if(ClaimIndex == -1)
					{
						ItemOrClaim = "Item:";
						ClaimIndex = itemIndex;
					}
					if(ClaimIndex == -1 || StatusIndex == -1)
					{
						System.out.println("Point 1 not worked for link :");
					}
					else
					{
						System.out.println(ItemOrClaim);
					 	Claim = plainText.substring(plainText.indexOf(ItemOrClaim) , plainText.indexOf("Status:"));
						System.out.println(Claim);
						Claim = Claim.replaceAll(ItemOrClaim, "").trim().toString().trim();
						Claim = (Claim.replaceAll("^ +| +$|( )+", "$1"));
					}

					int exampleIndex = plainText.indexOf("Example:");
					StatusIndex = plainText.indexOf("Status:");
					if(exampleIndex == -1 || StatusIndex == -1)
					{
						System.out.println("Point 2 not worked for link :");
					}
					else
					{

						Status = plainText.substring(plainText.indexOf("Status:") , plainText.indexOf("Example:"));
						Status = Status.replaceAll("Status:", "").trim();
						Status = (Status.replaceAll("^ +| +$|( )+", "$1"));
						System.out.println(Status);
					}

					int originIndex = plainText.indexOf("Origins:");
					exampleIndex = plainText.indexOf("Example:");
					if(exampleIndex == -1 || originIndex == -1)
					{
						System.out.println("Point 3 not worked for link :");
					}
					else
					{
						Example = plainText.substring(plainText.indexOf("Example:") , plainText.indexOf("Origins:"));
						Example = Example.replaceAll("Example:", "").trim();
						System.out.println(Example);
						Example = (Example.replaceAll("^ +| +$|( )+", "$1"));
					}

					originIndex = plainText.indexOf("Origins:");
					int lastUpdated = plainText.indexOf("Last updated:");
					if(lastUpdated == -1 || originIndex == -1)
					{
						System.out.println("Point 4 not worked for link :");
					}
					else
					{
						Origins = plainText.substring(plainText.indexOf("Origins:") , plainText.indexOf("Last updated:"));
						Origins = Origins.replaceAll("Origins:", "").trim();
						Origins = (Origins.replaceAll("^ +| +$|( )+", "$1"));
						System.out.println(Origins);
					}
					int sourcesIndex = plainText.indexOf("Sources:");
					lastUpdated = plainText.indexOf("Last updated:");
					if(lastUpdated == -1 || sourcesIndex == -1)
					{
						System.out.println("Point 4 not worked for link :");
					}
					else
					{
						LastUpdated = plainText.substring(plainText.indexOf("Last updated:") , plainText.indexOf("Sources:"));
						LastUpdated = LastUpdated.replaceAll("Last updated:", "").trim();
						LastUpdated = (LastUpdated.replaceAll("^ +| +$|( )+", "$1"));
						System.out.println(LastUpdated);
					}

					String Sources="";
					if(sourcesIndex != -1)
					{
						Sources = plainText.substring(plainText.indexOf("Sources:") );
						Sources = Sources.replaceAll("Sources:", "").trim();
						Sources = (Sources.replaceAll("^ +| +$|( )+", "$1"));
						System.out.println(Sources);
					}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("@@@begin_claim@@@"+Claim+"@@@end_claim@@@");
				bw.write("\r\n");
				bw.write("@@@begin_status@@@"+Status+"@@@end_status@@@");
				bw.write("\r\n");
				bw.write("@@@begin_example@@@"+Example+"@@@end_example@@@");
				bw.write("\r\n");
				bw.write("@@@begin_origins@@@"+Origins+"@@@end_origins@@@");
				bw.write("\r\n");
				bw.write("@@@begin_sources@@@"+Sources+"@@@end_sources@@@");
				
				bw.close();


					//exampleLinks = doc.select("div[style^=text-align: justify; margin-top: 10px; margin-bottom: 10px; margin-left: 15px; margin-right: 15px]");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void extractShikha()
	{

		try {
			Document doc = Jsoup.connect("http://www.snopes.com/horrors/horrors.asp").get();

			Elements links = doc.select("a[href]");
			int i = 0;
			for(Element link : links)
			{
				String str = link.attr("abs:href");
				String pattern = "^(http|https)://.*horrors";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(str);
				if(m.find())
				{

					//System.out.println(str);
					Document doc1 = Jsoup.connect(str).get();
					Elements links1 = doc1.select("a[href]");

					for(Element link1 : links1)
					{
						String str1 = link1.attr("abs:href");
						String pattern1 = "^(http|https)://.*horrors";
						Pattern r1 = Pattern.compile(pattern1);
						Matcher m1 = r1.matcher(str1);
						if(m1.find())
						{
							++i;
							System.out.println(str1);
						}
					}
				}


				//System.out.println("link : "+link);
			}
			System.out.println("i = "+i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void extract()
	{
		try {
			Document doc = Jsoup.connect("http://www.snopes.com/rumors/rumors.asp").get();

			Elements links = doc.select("a[href]");
			int i = 0;
			for(Element link : links)
			{
				String str = link.attr("abs:href");
				String pattern = "^(http|https)://.*rumors";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(str);
				if(m.find())
				{
					++i;
					System.out.println(str);
				}


				//System.out.println("link : "+link);
			}
			System.out.println("i = "+i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

public class DataExtraction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Extraction ext = new Extraction();
		ext.populateArrayList();
		ext.extractFromURL();

	}

}
