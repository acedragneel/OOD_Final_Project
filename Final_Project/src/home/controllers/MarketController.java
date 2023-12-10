package home.controllers;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;


public class MarketController implements Initializable {
	@FXML
	private Button btn1apply;
	@FXML
	private Button btn2apply;
	@FXML
	private Button btn3apply;
	@FXML
	private Button btn4apply;
	@FXML
	private Button btn5apply;
	@FXML
	private Button btn6apply;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("hello");
		
		btn1apply.setOnAction(arg0 -> {
			try {
				addOnClicks1(arg0);
			} catch (IOException | URISyntaxException e) {
				
				e.printStackTrace();
			}
		});
	}
	
	@FXML
	private void addOnClicks1(ActionEvent event) throws IOException, URISyntaxException {
		
		Desktop.getDesktop().browse(new URI("https://www.americanexpress.com/us/credit-cards/card-application/apply/blue-cash-everyday-credit-card/59000-10-0#/"));
		
		System.out.println("Card1");

	}
	@FXML
	private void addOnClicks2(ActionEvent event) throws IOException, URISyntaxException {
		
		Desktop.getDesktop().browse(new URI("https://www.americanexpress.com/us/credit-cards/card-application/apply/blue-cash-preferred-credit-card/59000-10-0#/"));
		
		System.out.println("Card2");

	}
	@FXML
	private void addOnClicks3(ActionEvent event) throws IOException, URISyntaxException {
		
		Desktop.getDesktop().browse(new URI("https://secure07ea.chase.com/web/oao/application/card?sourceCode=GRHR&action=guest#/origination/cardDetails/index/initiateConFullApp;cellCode=6D4N;cfgCode=FULLAPPCONCC;channel=C30;sourceCode=GRHR;AOC=6662;RPC=0534;combo=N;params=,,,no,no,,,"));
		
		System.out.println("Card3");

	}
	@FXML
	private void addOnClicks4(ActionEvent event) throws IOException, URISyntaxException {
		
		Desktop.getDesktop().browse(new URI("https://secure08ea.chase.com/web/oao/application/card?sourceCode=GML4&action=guest#/origination/cardDetails/index/initiateConFullApp;cellCode=6RRW;cfgCode=FULLAPPCONCC;channel=C30;sourceCode=GML4;AOC=6530;RPC=0444;combo=N;params=,,,no,no,,,"));
		
		System.out.println("Card4");

	}
	@FXML
	private void addOnClicks5(ActionEvent event) throws IOException, URISyntaxException {
		
		Desktop.getDesktop().browse(new URI("https://www.sofi.com/credit-card/"));
		
		System.out.println("Card5");

	}
	@FXML
	private void addOnClicks6(ActionEvent event) throws IOException, URISyntaxException {
		
		Desktop.getDesktop().browse(new URI("https://card.americanexpress.com/d/platinum-card/?utm_mcid=&utm_source=google&utm_medium=cpc&utm_term=%2Bamerican%20%2Bexpress%20%2Bplatinum&utm_cmpid=19818681320&utm_adgid=150647806487&utm_tgtid=kwd-383918391531&utm_mt=p&utm_adid=653054564804&utm_dvc=c&utm_ntwk=g&utm_adpos=&utm_plcmnt=&utm_locphysid=9002058&utm_locintid=&utm_feeditemid=&utm_devicemdl=&utm_plcmnttgt=&utm_programname=brandcps&gclid=Cj0KCQjwocShBhCOARIsAFVYq0jOOiAUQpAwgSw49lrrQyqkGLReddiUBnaNVji8Sz48DNpZ7-IrnKkaAu5uEALw_wcB"));
		
		System.out.println("Card6");

	}
	
}


