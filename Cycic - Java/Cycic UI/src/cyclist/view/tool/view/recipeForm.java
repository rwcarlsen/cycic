package cyclist.view.tool.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import cyclist.view.component.View;

public class recipeForm extends View{
	public recipeForm(){
		super();
		init();
		recipeGrid.setHgap(5);
		recipeGrid.setVgap(8);
		topRecipeGrid.setHgap(5);
		topRecipeGrid.setVgap(8);
		setContent(topRecipeGrid);
		setContent(recipeGrid);
	}
	
	private GridPane topRecipeGrid = new GridPane();
	private GridPane recipeGrid = new GridPane();
	private ComboBox<String> recipiesList = new ComboBox<String>();
	private int rowNumber = 3;
	
	/**
	 * 
	 */
	public void init(){
		Label isoSelect = new Label("Select Recipe");
		topRecipeGrid.add(isoSelect, 0, 0);
		recipiesList.getItems().clear();
		
		recipiesList.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				recipiesList.getItems().clear();
				for(int i = 0; i < dataArrays.Recipes.size(); i++){
					recipiesList.getItems().add(dataArrays.Recipes.get(i).Name);
				}
				recipiesList.getItems().add("Add New Recipe");
			}
		});
		
		recipiesList.valueProperty().addListener(new ChangeListener<String>(){ 
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if (newValue == null) {
					// Do nothing
				} else if (newValue == "Add New Recipe") {
					rowNumber = 3;
					Nrecipe recipe = new Nrecipe();
					dataArrays.Recipes.add(recipe);
					recipeGenInfo(recipe);
				} else {
					for(Nrecipe recipe: dataArrays.Recipes) {
						if (recipe.Name == newValue) {
							loadRecipe(recipe);
						}
					}
				}
			}
		});
		topRecipeGrid.add(recipiesList, 1, 0);
		
		Button removeRecipeButt = new Button();
		removeRecipeButt.setText("Remove Recipe");
		removeRecipeButt.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				recipeGrid.getChildren().clear();
				if (recipiesList.getValue() == "Add New Recipe"){
					// Do nothing
				} else {
					dataArrays.Recipes.remove(recipiesList.getItems().indexOf(recipiesList.getValue()));
				}
			}
		});
		topRecipeGrid.add(removeRecipeButt, 2, 0);
	}
	
	/**
	 * 
	 */
	public void recipeGenInfo(final Nrecipe recipe){
		recipeGrid.getChildren().clear();
		Label name = new Label("Recipe Name");
		TextField recipeName = new TextField();
		recipeName.setText(recipe.Name);
		recipeName.setMinHeight(10);
		recipeName.textProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				recipe.Name = newValue;
			}
		});
		recipeGrid.add(name, 0, 0);
		recipeGrid.add(recipeName, 1, 0);
		
		Label basis = new Label("Basis");
		recipeGrid.add(basis, 0, 1);
		
		final ComboBox<String> basisBox = new ComboBox<String>();
		basisBox.getItems().add("Atom");
		basisBox.getItems().add("Mass");
		basisBox.valueProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				recipe.Basis = basisBox.getValue();
			}
		});
		basisBox.setValue(recipe.Basis);
		recipeGrid.add(basisBox, 1, 1);
		
		Label isotope = new Label("Isotopes");
		recipeGrid.add(isotope, 0, 2);
		
		Button addIso = new Button();
		addIso.setText("Add Isotope");
		addIso.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				addIsotope(recipe);
			}
		});
		recipeGrid.add(addIso, 1, 2);	
	}
	
	/**
	 * 
	 * @param recipe
	 */
	public void addIsotope(Nrecipe recipe){
		final isotopeData isoData = new isotopeData(); 
		Label isotope = new Label("Isotope");
		recipeGrid.add(isotope, 0, rowNumber);
		
		TextField isotopeNumber = new TextField();
		
		isotopeNumber.setMinSize(40, 20);
		isotopeNumber.setMaxSize(100, 20);
		isotopeNumber.setText("ZZAAA");
		
		isotopeNumber.textProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				isoData.Name = newValue;
			}
		});
		recipeGrid.add(isotopeNumber, 1, rowNumber);
		
		Label isotopeValue = new Label("Amount");
		
		recipeGrid.add(isotopeValue, 2, rowNumber);
		
		TextField isoWeightFrac = new TextField(){
			@Override public void replaceText(int start, int end, String text) {
				if (!text.matches("[a-z]")){
					super.replaceText(start, end, text);
				}
			}
			
			public void replaceSelection(String text) {
				if (!text.matches("[a-z]")){
					super.replaceSelection(text);
				}
			}
		};
		isoWeightFrac.textProperty().addListener(new ChangeListener<String>(){
			@Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				isoData.weightFrac = Double.parseDouble(newValue);
			}
		});
		recipeGrid.add(isoWeightFrac, 3, rowNumber);
		recipe.Composition.add(isoData);
		rowNumber += 1;
	}
	
	/**
	 * 
	 * @param recipe
	 */
	public void loadRecipe(final Nrecipe recipe){
		recipeGenInfo(recipe);
		
		for(final isotopeData iso : recipe.Composition){
			Label isotopeStored = new Label("Isotope");
			recipeGrid.add(isotopeStored, 0, rowNumber);
			
			TextField isotopeNumber = new TextField();
			
			isotopeNumber.setText(iso.Name);
			isotopeNumber.setMinSize(40, 20);
			isotopeNumber.setMaxSize(100, 20);
			
			isotopeNumber.textProperty().addListener(new ChangeListener<String>(){
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
					iso.Name = newValue;
				}
			});
			recipeGrid.add(isotopeNumber, 1, rowNumber);
			
			Label isotopeValue = new Label("Amount");
			recipeGrid.add(isotopeValue, 2, rowNumber);
			
			TextField isoWeightFrac = new TextField(){
				@Override public void replaceText(int start, int end, String text) {
					if (!text.matches("[a-z]")){
						super.replaceText(start, end, text);
					}
				}
				
				public void replaceSelection(String text) {
					if (!text.matches("[a-z]")){
						super.replaceSelection(text);
					}
				}
			};
			isoWeightFrac.textProperty().addListener(new ChangeListener<String>(){
				@Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
					iso.weightFrac = Double.parseDouble(newValue);
				}
			});
			isoWeightFrac.setText(String.valueOf(iso.weightFrac));
			isoWeightFrac.setMaxSize(80, 20);
			recipeGrid.add(isoWeightFrac, 3, rowNumber);
			
			rowNumber += 1;
		}
	}
}

