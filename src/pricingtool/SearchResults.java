/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricingtool;

/**
 *
 * @author bradl
 */

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchResults {
    
    public SearchResults(String q, boolean keywords, String keyw){
        itemList = new ArrayList<Item>();
        this.keyword = keyw;
        this.requireKeywords = keywords;
        this.input = q.replaceAll(" ", "+");
        this.query = "https://www.ebay.com/sch/i.html?_nkw=" + q + "&_in_kw=1&_ex_kw=&_sacat=0&LH_Sold=1&_udlo=&_udhi=&_samilow=&_samihi=&_sadis=15&_sargn=-1%26saslc%3D1&_salic=1&_sop=12&_dmd=1&_ipg=200&LH_Complete=1&_fosrp=1";
    }
    
    public void searchEbay() throws IOException{
        Document doc = Jsoup.connect(query).get();
        Elements prices = doc.select("span.bidsold");
        Elements dates = doc.select("span.tme");
        Elements titles = doc.select("h3.lvtitle");
        for(int i = 0; i < prices.size(); i++){
            Element price = prices.get(i);
            String p = price.text();
            if(p.length() < 13){
                String newP = p.replaceAll("[$]","");
                newP = newP.replace(",", "");
                double priceInt = Double.valueOf(newP);
                Element date = dates.get(i);
                String d = date.text();
                Item newItem = new Item(priceInt, input, d);
                if(requireKeywords == true){
                    if(keywordInTitle(titles.get(i).text().toLowerCase())){
                        itemList.add(newItem);
                    }
                }else{
                    itemList.add(newItem);
                }
                //System.out.println(p + " " + date.text() + "   " + i);
            }
        }

    }
    
    public ArrayList<Item> getItemList(){
        return itemList;
    }
    
    public double getAveragePrice(){
        double totalPrice = 0;
        for(Item item : itemList){
            totalPrice = totalPrice + item.getPrice();
        }
        return (totalPrice / itemList.size());
    }
    
    public String getTimeFrame(){
        //return "NEEDS WORK";
        if(itemList.size() > 0){
            return (itemList.get(itemList.size() - 1).getDate() + " ---- " + itemList.get(0).getDate());
        }else{
            return "";
        }
    }
    
    public boolean keywordInTitle(String title){
        if(title.contains(keyword)){
            return true;
        }else{
            return false;
        }
    }
    
    private final String input;
    private final String query;
    private final String keyword;
    private boolean requireKeywords;
    private ArrayList<Item> itemList;
    
}
