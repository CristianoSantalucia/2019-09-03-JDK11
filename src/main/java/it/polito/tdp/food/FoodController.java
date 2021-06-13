/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Portion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController
{
	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtCalorie"
	private TextField txtCalorie; // Value injected by FXMLLoader

	@FXML // fx:id="txtPassi"
	private TextField txtPassi; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalisi"
	private Button btnAnalisi; // Value injected by FXMLLoader

	@FXML // fx:id="btnCorrelate"
	private Button btnCorrelate; // Value injected by FXMLLoader

	@FXML // fx:id="btnCammino"
	private Button btnCammino; // Value injected by FXMLLoader

	@FXML // fx:id="boxPorzioni"
	private ComboBox<Portion> boxPorzioni; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	private final String ERRORE = "\n\nERRORE! controllare che i dati inseriti siano corretti";
	
	@FXML void doCreaGrafo(ActionEvent event)
	{
		try
		{
			//controlli input
			Integer calorie;
			try
			{
				calorie = Integer.parseInt(this.txtCalorie.getText());
				if(calorie < 0)
				throw new NumberFormatException();
			}
			catch(NumberFormatException nfe)
			{
				this.txtResult.appendText("\n\nErrore, inserire un numero intero > 0");
				return;
			}  

			//resetto testo
			this.txtResult.clear();
	    	this.txtResult.appendText("Crea grafo...\n");

	    	//creo grafo
	    	this.model.creaGrafo(calorie);
	    	txtResult.appendText(String.format("\nGRAFO CREATO CON:\n#Vertici: %d\n#Archi: %d",
					this.model.getNumVertici(),
					this.model.getNumArchi()));

			//cliccabili
			this.btnCammino.setDisable(false);
			this.btnCorrelate.setDisable(false);
			this.boxPorzioni.setDisable(false);
			this.txtPassi.setDisable(false);

			//aggiungo risultati alla combobox 
			this.boxPorzioni.getItems().addAll(this.model.getVertici()); 
		}
		catch(Exception e)
		{
			this.txtResult.appendText(this.ERRORE);
		} 

	}
	
	@FXML void doCorrelate(ActionEvent event)
	{
		txtResult.appendText("Cerco porzioni correlate...");
	}
	
	@FXML void doCammino(ActionEvent event)
	{
		txtResult.appendText("Cerco cammino peso massimo...");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
		assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
		assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
		assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

	}

	public void setModel(Model model)
	{
		this.model = model;
	}
}
