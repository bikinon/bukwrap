package bukwrap;

public class LayerLineType {

 public String layer; 
  
  public String getLineColour() {
      // Return a colour for a layer from its Layer Name
      // Artios understands Colours only not Layers
      /* 
      See ArtiosCAD: Defaults > Import Tuning Table > Artios DWG/DXF - Metric
      0 = Glue assist  (DARK GREEN)
      1 = Cut (BLACK)
      2 = Crease (RED)
      3 = PERF 3x3
      4 = Partial Cut (BLACK)
      5 = PERF 6x6 
      6 = 6 Cut/Cre
      7 = Glue assist  (DARK GREEN)
      8 = Reverse Crease (RED WITH MARK)
      9 = Reverse Partial Cut (DARk RED? WITH MARK)
      10 = Print Registration (DARK GREEN?)
      11 = Outside Bleed (LIGHT GREEN)
      12 = 12 Cut/Cre
      14 = SAFETY EDGE
      15 = Matrix
      20 = Annotation   (LIGHT GREEN)
      Default in CAD = Annotation   (LIGHT GREEN)
      Default in Java = CUT
      */
      String col = "1";
      switch (layer) {
        case "CUT" : 
        col = "1"; 
        break;
        case "CREASE" : 
        col = "2"; //
        break;
        case "PERF3" : 
        col = "3"; // 3 Cut, 3 Gap  
        break;
        case "PartialCut" : 
        col = "4"; // Cut through all but outer facing
        break;  
        case "PERF6" : 
        col = "5"; // 6 Cut, 6 Gap   
        break; 
        case "CUTCRE6" : 
        col = "6"; // 6 Cut, 6 Crease   
        break; 
        case "GlueAssist" : 
        col = "7"; 
        break;
        case "ReverseCrease" : 
        col = "8"; // 
        break;
        case "ReversePartialCut" : 
        col = "9"; 
        break;    
        case "PrintRegistration" : 
        col = "10"; 
        break;  
        case "OutsideBleed" : 
        col = "11"; 
        break;   
        case "CUTCRE12" : 
        col = "12";  // 12 Cut, 12 Crease
        break;
        case "SAFETY" : 
        col = "14"; // Safety Deckle Edge
        break;     
        case "Annotation" : 
        col = "20";
        break;       
        default: 
        col = "1";
      } // end of switch
      return col;  
  } // getLineColour
  
  
} // LayerLineType
