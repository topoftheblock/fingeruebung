

   
/* Apache UIMA v3 - First created by JCasGen Sat May 02 11:29:55 CEST 2026 */

package org.texttechnologylab.practical.sose26.christian.type;
 

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;

import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.impl.TypeSystemImpl;
import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;


import org.apache.uima.jcas.tcas.Annotation;


/** Session Information
 * Updated by JCasGen Sat May 02 11:29:55 CEST 2026
 * XML source: /Users/andreasblock/Desktop/6.Semester/Praktikum/Fingeruebung/target/jcasgen/typesystem.xml
 * @generated */
public class Sitzungsinformationen extends Annotation {
 
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static String _TypeName = "org.texttechnologylab.practical.sose26.christian.type.Sitzungsinformationen";
  
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Sitzungsinformationen.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
 
  /* *******************
   *   Feature Offsets *
   * *******************/ 
   
  public final static String _FeatName_sitzungsNummer = "sitzungsNummer";


  /* Feature Adjusted Offsets */
  private final static CallSite _FC_sitzungsNummer = TypeSystemImpl.createCallSite(Sitzungsinformationen.class, "sitzungsNummer");
  private final static MethodHandle _FH_sitzungsNummer = _FC_sitzungsNummer.dynamicInvoker();

   
  /** Never called.  Disable default constructor
   * @generated */
  @Deprecated
  @SuppressWarnings ("deprecation")
  protected Sitzungsinformationen() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param casImpl the CAS this Feature Structure belongs to
   * @param type the type of this Feature Structure 
   */
  public Sitzungsinformationen(TypeImpl type, CASImpl casImpl) {
    super(type, casImpl);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Sitzungsinformationen(JCas jcas) {
    super(jcas);
    readObject();   
  } 


  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Sitzungsinformationen(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: sitzungsNummer

  /** getter for sitzungsNummer - gets Number of the session
   * @generated
   * @return value of the feature 
   */
  public int getSitzungsNummer() { 
    return _getIntValueNc(wrapGetIntCatchException(_FH_sitzungsNummer));
  }
    
  /** setter for sitzungsNummer - sets Number of the session 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSitzungsNummer(int v) {
    _setIntValueNfc(wrapGetIntCatchException(_FH_sitzungsNummer), v);
  }    
    
  }

    