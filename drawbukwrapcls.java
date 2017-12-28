package bukwrap;
/**
  *
  * Description
  *
  * @version 1.0 from 8/3/2016
  * @author Tim Gathercole
  */

public class drawbukwrapcls extends dxf12objects {
  
  // start attributes
  public double length = 0;
  public double width = 0;
  public double depth = 0;
  public int noBends = 1;
  public double mindepth = 0;
  public double buffer = 20.5;
  public double psflap = 65;
  public String flute = "E";
  public String dimType = "ID: Internal Dimensions";
    
  // allowance panels
  public double lmain = 0; // length main
  public double winr = 0; //
  public double wotr1 = 0;
  public double wotr2 = 0;
  public double dinr = 0; //
  public double dotr1 = 0;
  public double dotr2 = 0;
  public double ccCutBack = 15;  // cut crease cut back (crease section)
  public double fingerRad = 12; // half moon finger cut-out
  public double hNik = 1.5;   // Horizontal Nicks
  public double vNik = 2;
  
  public double s1way = 0;
  public double n2way = 0;  
  
  public String CUT = "CUT";
  public String CREASE = "CREASE";
  public int col = 1; // Line Colour
  public String ltype = "CONTINUOUS"; // Line Type
  
  // end attributes
  
  // start methods
  public String drawbukwrap() {
    // Set EXTENTS
    //    dxfymax = String.valueOf(wouter * 2 + (dmain * 3));
    //    dxfxmax = String.valueOf((dmain + dblbend + toeflap) * 2 + lmain);
    // DimensionSizes(0.0, 0.0, Double.parseDouble(dxfxmax), Double.parseDouble(dxfymax));
    allowanceSetup();
    makeupCheck();
    
    dxf += dxf_header12();
    
    // draw the main body starting main depth btm right
    double btmLn = ((lmain + buffer * 2) - (fingerRad * 2)) / 2;
    Line(-btmLn, 0, "SAFETY"); 
    arc2((xabs - fingerRad), yabs, fingerRad, 0, 180, -fingerRad*2, 0, "SAFETY");
    Line(-btmLn, 0, "SAFETY"); 
    
    mainBody();
    
    wrapSection();
    
    double topang = 14;
    double cutbk = 4;
    absMove(0, winr + wotr1 + dotr1 + wotr2 + dotr2);
    /*
    psTopFlapSide(topang, cutbk);
    
    Line(-lmain - (buffer * 2) + ((topang + cutbk) * 2), 0, CUT); 
    relMove(-topang - cutbk, -psflap);
    Xaxis = -1; // flip UCS
    Yaxis = 1;
    psTopFlapSide(topang, cutbk);
    */   
    this.psTopFlap_RC_Current();
    
    
    dxf_footer12();
    
    return dxf;
  } // drawbukwrap
  
  
  protected boolean psTopFlapSide(double topang, double cutbk) {
    double psgap = 22;
    double rippagap = 10;
    double tabang = 10;
    double tablen = 29;
    
    Line(0, psflap - tabang - rippagap - psgap, CUT);
    relMove(-tablen, tabang);
    Line(tablen, -tabang, CUT);
    Line(0, tabang + rippagap, CUT); 
    Line(-tablen, 0, CUT);
    
    relMove(tablen - cutbk, 0);
    Line(-topang, psgap, CUT);
    
    return false;
  } // psTopFlapSide  
  
  
  protected void psTopFlap_Lil_Version() {
  // Top Flap - Peel & Seal / Rippa Tape section    
    double bang = 13;
    double bAngUp = 10;
    double bangStr = 2;
    double tabR = 8.5;
    double tabLen = 23;
    double tabAng = 4;
    double ps = 20;
    double arcIn = 10;
    double strght = this.psflap - (bAngUp+bangStr+(tabR*2)+ps);
    
    Line(0, strght, CUT);
    Line(-bang, bAngUp, CUT);
    Line(0, bangStr, CUT);
    Line(-tabLen, tabAng, "SAFETY");
    relMove(tabLen, -tabAng);
    this.arc2(this.xabs, this.yabs + tabR, tabR, 270, 90, 0, (tabR * 2), "SAFETY");
    Line(-tabLen, 0, "SAFETY");
    relMove(tabLen, 0);
    Line(-arcIn, ps, "SAFETY");
    
    Line(-lmain -(buffer * 2) + ((bang + arcIn) * 2), 0, "SAFETY");
    
    relMove(-(bang + arcIn), -this.psflap);
    Line(0, strght, CUT);
    Line(bang, bAngUp, CUT);
    Line(0, bangStr, CUT);
    Line(tabLen, tabAng, "SAFETY");
    relMove(-tabLen, -tabAng);
    this.arc2(this.xabs, this.yabs + tabR, tabR, 90, 270, 0, (tabR * 2), "SAFETY");
    Line(tabLen, 0, "SAFETY");
    relMove(-tabLen, 0);
    Line(arcIn, ps, "SAFETY");  
  } // psTopFlap
  
  
  
  protected void psTopFlap_RC_Current() {
  // Top Flap - Peel & Seal / Rippa Tape section    
    double tabBang = 5;
    double tabTang = 3.5;
    double crnVert = 5;
    double crnHorz = 5;
    double tabRippaStrt = 19;
    double tabLen = 20;
    double tabTotVert = 21;
    double ps = 20;
    double psAng = 4;
    double strght = this.psflap - (tabTotVert - tabTang + ps);
    double xtmp = 0, ytmp = 0;
    
    // Right Side PS Tab Section
    Line(0, strght, CUT);
    Line(-tabLen, tabBang, CUT);
    relMove(tabLen, -tabBang); // Move back & continue drawing Tab
    Line(0, tabTotVert - crnVert, CUT);
    Line(-crnHorz, crnVert, CUT);
    Line(-(tabLen - crnHorz), -tabTang, CUT);
    relMove(tabLen - crnHorz, tabTang); // Move back & continue drawing Tab
    Line(-psAng, tabRippaStrt - tabTang, CUT);
    
    Line(-lmain -(buffer * 2) + ((crnHorz + psAng) * 2), 0, "CUT");
    
    // Left Side PS Tab Section
    Line(-psAng, -(tabRippaStrt - tabTang), CUT);
    Line(tabLen - crnHorz, -tabTang, CUT);
    relMove(-(tabLen - crnHorz), tabTang); // Move back & continue drawing Tab
    Line(-crnHorz, -crnVert, CUT);
    Line(0, -(tabTotVert - crnVert), CUT);
    Line(tabLen, tabBang, CUT);
    relMove(-tabLen, -tabBang); // Move back & continue drawing Tab
    Line(0, -strght, CUT);
    xtmp = this.xabs; // Store
    ytmp = this.yabs;
    
  // **** Peel & Seal / Rippa Tape ****
  double psEdgeGap = 3;
  double psTopDown = 2;
  double psWidth = 18;
  double ripperWidth = 4;
  double psRipGap = 4;
  double psTopLine = lmain + (buffer * 2) - ((crnHorz + psEdgeGap) * 2);
  
  relMove(crnHorz + psEdgeGap, psflap - psTopDown);
  Line(psTopLine, 0, "Annotation");  
  relMove(0, -psWidth);
  Line(-psTopLine, 0, "Annotation");  
  relMove(0, -psRipGap);
  Line(psTopLine, 0, "Annotation");
  relMove(0, -ripperWidth);
  Line(-psTopLine, 0, "Annotation");
  
  TextInsert((psTopLine / 2) - 15, 14, "Peel & Seal", "Annotation");
  TextInsert((psTopLine / 2) - 15, -1, "Rippa Tape", "Annotation");
  
  } // psTopFlap_RC_Current
  
  
  
  protected boolean wrapSection() {
    // Wrap body section
    absMove(0, winr);
    Line(0, wotr1 + dotr1 + wotr2 + dotr2, CUT);
    relMove(-lmain -(buffer * 2), 0);
    Line(0, -wotr1 - dotr1 - wotr2 - dotr2, CUT);
    
    relMove(0, wotr1);
    Line(lmain + (buffer * 2), 0, CREASE);
    // Ghost additonal Creases 
    relMove(-(lmain + (buffer * 2)), dotr1);
    Line(lmain + (buffer * 2), 0, "Annotation");
    relMove(0, wotr2);
    Line(-(lmain + (buffer * 2)), 0, "Annotation");    
    relMove(0, dotr2);
    Line(lmain + (buffer * 2), 0, "Annotation");
    
    return true;  
  }  
  
  protected boolean mainBody() { 
    // Bottom of main base
    Line(0, winr, CUT);
    Line(buffer, 0, CREASE);
    
    // Line(lmain, 0, CUT);
    notchedHorzontalLine();
    
    Line(buffer, 0, CREASE);
    Line(0, -winr, CUT);
    
    relMove(-buffer, 0);
    
    Line(0, ccCutBack, CREASE);
    Line(0, winr - (ccCutBack * 2), "CUTCRE12");
    Line(0, ccCutBack, CREASE);
    
    if (mindepth < 1) {
      relMove(-dinr, 0);  // ****
      Line(0, -winr, CREASE);
    } else {
      relMove(-mindepth, 0);  // ****
      Line(0, -winr, CREASE);
      relMove(-(dinr - mindepth), winr);  // *Keep going the same direction
      Line(0, -winr, CREASE);       
    }  // end of if
    
    
    relMove(-lmain+dinr, 0);
    Line(0, ccCutBack, CREASE);
    Line(0, winr - (ccCutBack * 2), "CUTCRE12");
    Line(0, ccCutBack, CREASE);
    
    relMove(dinr, 0);  // ****
    Line(0, -winr, CREASE);
    
    absMove(-((lmain / 2) + buffer), winr);
    notchedVerticalLine();
    // Next section does an absMove, so we dont care where we finish
    
    return false;
  }
  
  protected boolean notchedVerticalLine() {
    double dwn = (winr - fingerRad) / 2;
    double kick = 15;
    
    Line(kick, -dwn, CUT);
    Line(-kick, -dwn, CUT);
    
    return false;
  } // notchedVerticalLine
  
  
  protected boolean notchedHorzontalLine() {
    double cutLen = 45 - hNik; 
    int noCut = (int) ((lmain / (cutLen + hNik)) + 0.5);  
    double endCut = (lmain - ((noCut - 2) * (cutLen + hNik))) / 2;
    endCut = endCut - (hNik / 2); // only one notch before we start to draw the matched pairs of Cut & Notch
    
    Line(endCut, 0, CUT);
    relMove(hNik, 0);
    
    for (int i = 0; i < (noCut - 2); i++) {
      Line(cutLen, 0, CUT);
      relMove(hNik, 0);       
    } // end of for
    
    Line(endCut, 0, CUT);
    
    return false;
  }
  
  
  protected boolean allowanceSetup() {
    // allowances 
    if (flute.equals("E")) {
      lmain = length + 2; // length main
      winr = width - 2; //
      wotr1 = width + 2;
      wotr2 = width + 3;
      dinr = depth + 2; //
      dotr1 = depth + 3;
      dotr2 = depth + 4;
      hNik = 1.5;
      vNik = 2;  
    
    } else if (flute.equals("B")) {
      lmain = length + 3; // length main
      winr = width - 1; //
      wotr1 = width + 3;
      wotr2 = width + 5;
      dinr = depth + 3; //
      dotr1 = depth + 5;
      dotr2 = depth + 8;  
      hNik = 2;
      vNik = 2.5;       
    
    } else { // C Flute
      lmain = length + 5; // length main
      winr = width - 2; //
      wotr1 = width + 5;
      wotr2 = width + 8;
      dinr = depth + 5; //
      dotr1 = depth + 8;
      dotr2 = depth + 12; 
      hNik = 2;
      vNik = 2.5;           
    } // end of if-else
    
    return true; 
  }   
 
  
  protected void makeupCheck() {
    
    if (psflap > winr) {
      javax.swing.JOptionPane.showMessageDialog(null,
      "Error: Peel & Seal Flap > Width\nFlap will overlap the base of the box and not seal", "Error Message",
      javax.swing.JOptionPane.ERROR_MESSAGE);
    } // end of if  
    if (psflap < 42) {
      javax.swing.JOptionPane.showMessageDialog(null,
      "Error: 42mm is ABSOLUTE MINIMUM for P&S Flap\nMake up problems likely even at 42mm, 54mm recommended minimum size.", "Error Message",
      javax.swing.JOptionPane.ERROR_MESSAGE);
    } // end of if  
    if (psflap < 54) {
      javax.swing.JOptionPane.showMessageDialog(null,
      "WARNING: 54mm is the minimum recommended P&S Flap Size.\n65mm is Std & 75mm is recommended for larger Width sizes.", "Make-up Warning",
      javax.swing.JOptionPane.WARNING_MESSAGE);
    } // end of if  
    if (dinr * 2 + fingerRad >= lmain) {
      javax.swing.JOptionPane.showMessageDialog(null,
      "Error: Length < Depths + Finger Radius\nNot enough room in length for other sections of pack.", "Error Message",
      javax.swing.JOptionPane.ERROR_MESSAGE);
    } // end of if     
    if (buffer < 20.5) {
      javax.swing.JOptionPane.showMessageDialog(null,
      "Error: Gluer will not be able to flip panel accurately with a Buffer < 20.5.", "Error Message",
      javax.swing.JOptionPane.ERROR_MESSAGE);
    } // end of if     
    
  } // makeupCheck
  
  
   protected String DimensionSizes(double llx, double lly, double urx, double ury) {
    double x = Math.abs(llx - urx);
    double y = Math.abs(lly - ury);
    //    double sv = (x / 32); //Line Scale
    String err = ""; // Later version might return an error if necessary
    double txv = 1;
    
    if (y * 1.2 > x) {
      txv = y;
    } else {
      txv = x;
    }
    //(command "_LTSCALE" sv)
    dimasz = String.valueOf(txv / 40);  // Arrow Size
    dimtxt = String.valueOf(txv / 65);  // Text Size
    dimexe = String.valueOf(txv / 260); // Extension of witness line above dimension line
    
    dimexo = String.valueOf(txv / 100); // Extension line origin offset (was 260)
    dimgap = String.valueOf(txv / 70);  // Gap from dimension line to text (was 90)
    
    /*
    (command "_.LAYER" "_SET" "DIMS" ""
    "DIMASZ" (/ txv 40.0)  ; Arrow Size
    "DIMTXT" (/ txv 65.0)  ; Text Size
    "DIMEXE" (/ txv 260.0) ; Extension of witness line above dimension line
    "LUNITS" Unt           ; Drawing Units
    "DIMUNIT" DimUnt       ; Dim Units *Obsolete. Replaced by DIMLUNIT and DIMFRAC?
    "LUPREC" DecP          ; drawing decimal Precesion
    "DIMDEC" DimP          ; Dim decimal Precision
    "DIMZIN" 8             ; Suppresses trailing zeros in Decimals
    "DIMAZIN" 2            ; Suppresses trailing zeros in Decimals - v ICAD 6
    ; ----------------------------------------------------
    "DIMEXO" (/ txv 260.0) ; Extension line origin offset
    "DIMGAP" (/ txv 90.0)  ; Gap from dimension line to text
    )
    */
    return err;
  }

  
  protected void plantLimitsCheck() {
    
  }  
  
  public void calcBlkSz() {
    allowanceSetup();
    makeupCheck();
    
    this.n2way = this.winr + this.wotr1 + this.dotr1 + this.wotr2 + this.dotr2 + this.psflap;
    this.s1way = this.buffer * 2 + this.lmain;
    
  }  // calcBlkSz
  
  
  public String calcBnds() {
    
    return "";
  } // calcBnds
  
  // end methods
} // end of drawbukwrap
