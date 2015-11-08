import java.awt.*;import java.awt.event.*;import java.awt.image.*;
import java.io.*;import javax.imageio.*;import javax.swing.*;
import java.awt.*;import java.awt.image.*;
import java.awt.image.BufferedImage;import java.io.*;
//import pkg1.thresold;
/**
 * This class demonstrates how to load an Image from an external file
 */
public class graphpoint extends Component {

BufferedImage img;
int[] pixels; int[][] newp; int[][] feature; int[] newpixels; int[] newpixels1; int[] newpixels2;int[] hist;int[] histcdf;int[] histeq;
int[][] pixel2d;int[][] gx;int[][] gy;int[] Fxpoint;int[] Fypoint;int[][] FXYcost;float[] Fangle;float[][] EUdist;int[] Fxy;int[][] levels;int[][] height;

int w,iw,imw;
             int h,ih,imh;
             int X1,Y1,Xm,Ym;
             int count=0;
             int[][] gxmask = new int[3][3];        //Sobel mask in X direction
             int[][] gymask = new int[3][3];        //Sobel mask in Y direction
             int newpixel;                        //Sum pixel value for gussian
             int[][] gaussianmask = new int[5][5];   //Gaussian Mask
             float[][] edgedir;
             float[][] Diffx;
             float[][] Diffy;
             float[][] gradient;   //store the gradient strength of each pixel.............................
             int row,col;                      //pixel's Row and Col position
             int i,rgb,b;                         //Dummy variable for row col vector
             int ioffset;                     //Variable to offset row- col vector during sobel mask
             int rowoffset,coloffset;             //Row and Col offsetfrom Current Pixel
             int rowtotal=0;int coltotal=0;        //Row Col Position of offset pixel
             int gx1,gy1;                      //Sum Of sobel maskproduct value in x & ny direction
             float thisangle;                  //Gradient direction based on Gx and Gy
             float newangle=0;
             float value=0;
             
             //For Thinning Process..........
               int[][] m1 = {{255,255,255},{255,0,255},{255,255,255}} ;
               int[][] m2 = {{0,0,0},{0,0,255},{0,0,255}} ;
               int[][] m3 = {{0,0,255},{0,0,255},{0,0,0}} ;
               int[][] m4 = {{255,255,0},{0,0,0},{0,0,0}} ;
               int[][] m5 = {{0,255,255},{0,0,0},{0,0,0}} ;
               int[][] m6 = {{0,255,255},{0,0,255},{0,0,255}} ;
               int[][] m7 = {{255,255,255},{0,0,255},{0,0,0}} ;
               int[][] m8 = {{0,0,0},{0,0,255},{0,0,0}} ;
               int[][] m9 = {{0,255,0},{0,0,0},{0,0,0}} ;
               int[][] m10 = {{0,0,255},{0,0,255},{0,255,255}} ;
               int[][] m11 = {{0,0,0},{0,0,255},{255,255,255}} ;
               int[][] m12 = {{255,255,255},{255,0,0},{0,0,0}} ;
               int[][] m13 = {{255,255,0},{255,0,0},{0,0,0}} ;
               int[][] m14 = {{0,0,0},{255,0,0},{255,255,255}} ;
               int[][] m15 = {{255,0,0},{255,0,0},{255,255,0}} ;
               int[][] m16 = {{0,0,0},{255,0,0},{0,0,0}} ;
               int[][] m17 = {{0,0,0},{0,0,0},{0,255,0}} ;
               int[][] m18 = {{0,0,0},{255,0,0},{255,0,0}} ;
               int[][] m19 = {{255,0,0},{255,0,0},{0,0,0}} ;
               int[][] m20 = {{0,0,0},{0,0,0},{255,255,0}} ;
               int[][] m21 = {{0,0,0},{0,0,0},{0,255,255}} ;

        //For Thinning Process for Diagonal Pixels......................................
               int[][] d1 = {{0,255,255},{0,0,255},{0,0,0}} ;
               int[][] d2 = {{0,0,0},{255,0,0},{255,255,0}} ;
               int[][] d3 = {{255,255,0},{255,0,0},{0,0,0}} ;
               int[][] d4 = {{0,0,0},{0,0,255},{0,255,255}} ;

               //.....................................................................
               int[][] b1 = {{255,0,255},{255,0,255},{0,255,0}} ;
               int[][] b2 = {{0,255,0},{255,0,255},{255,0,255}} ;
               int[][] b3 = {{0,255,255},{255,0,0},{0,255,255}} ;
               int[][] b4 = {{255,255,0},{0,0,255},{255,255,0}} ;
               int[][] b5 = {{255,255,255},{0,0,0},{255,0,255}} ;
               int[][] b6 = {{255,0,255},{0,0,0},{255,255,255}} ;
               int[][] b7 = {{255,0,255},{0,0,255},{255,0,255}} ;
               int[][] b8 = {{255,0,255},{255,0,0},{255,0,255}} ;
               int[][] b9 = {{0,255,255},{255,0,255},{0,255,0}} ;
               int[][] b10 = {{0,255,0},{255,0,255},{0,255,255}} ;
               int[][] b11 = {{255,255,0},{255,0,255},{0,255,0}} ;
               int[][] b12 = {{0,255,0},{255,0,255},{255,255,0}} ;
               int[][] b13 = {{0,255,255},{0,0,0},{255,0,255}} ;
               int[][] b14 = {{255,255,0},{0,0,0},{255,0,255}} ;
               int[][] b15 = {{255,0,255},{0,0,0},{0,255,255}} ;
               int[][] b16 = {{255,0,255},{0,0,0},{255,255,0}} ;
               int[][] b17 = {{255,0,255},{0,0,255},{255,255,0}} ;
               int[][] b18 = {{0,255,255},{255,0,0},{255,0,255}} ;
               int[][] b19 = {{255,255,0},{0,0,255},{255,0,255}} ;
               int[][] b20 = {{255,0,255},{255,0,0},{0,255,255}} ;
               int[][] b21 = {{0,0,255},{255,0,0},{255,0,255}} ;
               int[][] b22 = {{255,0,255},{255,0,0},{0,0,255}} ;
               int[][] b23 = {{255,0,255},{0,0,255},{255,0,0}} ;
               int[][] b24 = {{255,0,0},{0,0,255},{255,0,255}} ;





     public graphpoint( BufferedImage im) {

           img = im;
  try{
                iw=img.getWidth(null); imw=iw/2;
                ih=img.getHeight(null); imh=ih/2;
                pixels = new int[iw * ih];
                hist = new int[256];
                histcdf = new int[256];
                gx = new int[ih][iw];
                gy = new int[ih][iw];
                histeq = new int[256];
                Diffx = new float[ih][iw];
                Diffy = new float[ih][iw];
                newpixels = new int[iw * ih];
                newpixels1 = new int[200 * 200];
                newpixels2 = new int[200 * 200];
                edgedir = new float[ih][iw];
                gradient = new float[ih][iw];
                newp = new int[200][200];
                feature= new int[200][200];
                height = new int[5][];

              PixelGrabber pg = new PixelGrabber(img, 0, 0, iw, ih, pixels, 0, iw);
                 pg.grabPixels();
     }
             catch(InterruptedException e){System.out.println ("Interrupted"); return;}

  }
  
  
  
  //.......................gaussianmask PROCESS..............................
  
  
 void gaussian(int t)
     {
        //Declare Sobel Mask for Gx...........................
          gxmask[0][0]=-1;gxmask[0][1]=0;gxmask[0][2]=1;
          gxmask[1][0]=-2;gxmask[1][1]=0;gxmask[1][2]=2;
          gxmask[2][0]=-1;gxmask[2][1]=0;gxmask[2][2]=1;

      //Declare Sobel Mask for Gy...........................
          gymask[0][0]=1;gymask[0][1]=2;gymask[0][2]=1;
          gymask[1][0]=0;gymask[1][1]=0;gymask[1][2]=0;
          gymask[2][0]=-1;gymask[2][1]=-2;gymask[2][2]=-1;
      //Declare Gaussian Mask...............................
          gaussianmask[0][0]=2; gaussianmask[0][1]=4; gaussianmask[0][2]=5; gaussianmask[0][3]=4; gaussianmask[0][4]=2;
          gaussianmask[1][0]=4; gaussianmask[1][1]=9;  gaussianmask[1][2]=12; gaussianmask[1][3]=9; gaussianmask[1][4]=4;
          gaussianmask[2][0]=5; gaussianmask[2][1]=12; gaussianmask[2][2]=15; gaussianmask[2][3]=12; gaussianmask[2][4]=5;
          gaussianmask[3][0]=4; gaussianmask[3][1]=9; gaussianmask[3][2]=12; gaussianmask[3][3]=9; gaussianmask[3][4]=4;
          gaussianmask[4][0]=2; gaussianmask[4][1]=4; gaussianmask[4][2]=5; gaussianmask[4][3]=4; gaussianmask[4][4]=2;



     //Gaussian Blur To Denoise ....................
          for(row =2; row<ih-2; row++)
          {
               for(col=2 ; col<iw-2; col++)
              {
                    newpixel=0;b=0;
                   for(rowoffset=-2 ; rowoffset<=2 ;rowoffset++)
                     {
                       for(coloffset =-2; coloffset<=2 ;coloffset++)
                        {
                            rowtotal = row + rowoffset;
                            coltotal = col + coloffset;
                            ioffset = (rowtotal*iw+coltotal);
                          rgb=  pixels[ioffset];
                          b= rgb  & 0xff;
                            newpixel += b * gaussianmask[2+rowoffset][2+coloffset];
                         }
                      }
                    i=(row  *iw + col);
                    newpixel /= 159;

                    newpixels[i] = (0xff000000 | (newpixel << 16) |( newpixel << 8) | newpixel);

              }
           }


           //Histogram Analysis.....................................................

             for(int j=0; j<255; j++){ hist[j]=0;  histcdf[j]=0;}

                for(int i=0; i<iw*ih; i++)
                 {
                   rgb=newpixels[i];
                   b= rgb  & 0xff;
                   hist[b]++;
                  }

               histcdf[0]=hist[0];
                    for(int j=1; j<255; j++)
                        histcdf[j] = hist[j]+histcdf[j-1];


                     int cdfmin=iw*ih;
                   for(int j=0; j<255; j++){ if(histcdf[j]<cdfmin) cdfmin=histcdf[j];}

                      int temp=((iw*ih)-cdfmin);
                   for(int j=0; j<255; j++){ histeq[j]=(((histcdf[j]-cdfmin)*255)/temp);}

                  for(int i=0; i<iw*ih; i++)
                   {
                      rgb=newpixels[i];
                      b= rgb  & 0xff;
                      int  n =histeq[b];
                      newpixels[i]=(0xff000000 | (n << 16) |( n << 8) | n);
                    }


           for(row=0;row<ih;row++){
              for(col=0;col<iw;col++){
                  edgedir[row][col]=0; gx[row][col]=0; gy[row][col]=0;
                }
           }



           //Determine edge direction and gradient strength.................

                    for(row =1 ; row <ih-1 ; row++){
                        for(col =1 ; col <iw-1 ; col++){
                             i= (row*iw +col);
                             gx1=0;gy1=0;
                    //calculate the sum of sobel matrix with the 9 pixel cell..................
                         for(rowoffset=-1 ; rowoffset<=1 ;rowoffset++){
                                 for(coloffset =-1; coloffset<=1 ;coloffset++){
                                      rowtotal = row + rowoffset;
                                      coltotal = col + coloffset;
                                      ioffset = (rowtotal*iw+coltotal);
                                      rgb=  newpixels[ioffset];
                                      b= rgb  & 0xff;
                                      gx1 += (b * gxmask[1+rowoffset][1+coloffset]);
                                      gy1 += (b * gymask[1+rowoffset][1+coloffset]);
                                    }
                              }
                                gx[row][col]=gx1; gy[row][col]=gy1;
                               gradient[row][col] =(float) Math.sqrt(Math.pow(gx1,2.0)+Math.pow(gy1,2.0));

                          }
                     }

                    // for local ridge orientation..................
                         for(row=1;row<ih-1;row++){
                            for(col =1 ; col <iw-1 ; col++){
                                 float vx=0,vy=0;
                                 for(int i=0;i<3;i++){
                                     for(int j=0;j<3;j++){
                                     vx += (2*(gx[row-1+i][col-1+j])*(gy[row-1+i][col-1+j]));
                                     vy += ((Math.pow((gx[row-1+i][col-1+j]),2.0))-(Math.pow((gy[row-1+i][col-1+j]),2.0)));
                                     }
                                 }

                                thisangle =(float) Math.toRadians(((Math.atan(vy/vx))/(2)));

                                      newangle = thisangle;
                                edgedir[row][col]=newangle;//System.out.println(edgedir[row][col]);
                            }
                         }

                     for(row =3 ; row <ih-3 ; row+=7){
                        for(col =3 ; col <iw-3 ; col+=7){
                             //calculate the Angle Diff..................
                                    float temp1=0,temp2=0,temp3=0,temp4=0;
                                 for(int i=0;i<7;i++){

                                  temp1 +=  (Math.sin(2*(edgedir[row-3][col-3+i])));
                                  temp2 +=  (Math.sin(2*(edgedir[row+3][col+3-i])));
                                  temp3 +=  (Math.cos(2*(edgedir[row-3+i][col-3])));
                                  temp4 +=  (Math.cos(2*(edgedir[row+3-i][col+3])));


                                 }
                                  Diffx[row][col]= temp1-temp2;//System.out.println("Diffx  :" +Diffx[row][col]);
                                  Diffy[row][col]= temp3-temp4;//System.out.println("Diffy  :" +Diffy[row][col]);


                         }
                      }
                      
                      
       int x1,y1,x2,y2;int[][] templ=new int[200][200]; int[] templ1=new int[200*200];
        x1= 107-100;
        x2=107+100;
        y1=202-100;
        y2=202+100;

         for(int i=0,row =y1 ; row <y2 ; i++,row++){
                                 for(int j=0,col =x1 ; col <x2 ;j++, col++){
                                             templ[i][j]=newpixels[row * iw + col];
                                      }
                               }

//converting the 1d matrix....................................................
     int  l=0;
    for(int i=0;i<200;i++){
        for(int j=0;j<200;j++){
           templ1[l]=templ[i][j];//System.out.println(templ[i][j]);
           l++;
           }
         }



     // Binarising .................................

                  for(row =1; row<200 ;row++){
                     for(col =1; col<200;col++){
                         i=(row  *200 + col);
                         rgb= templ1[i];
                         b = rgb & 0xff;
                      if(b>40)
                         b=255;
                      else
                         b=0;
                      newpixels1[i] = (0xff000000 | (b << 16) |(b << 8) | b);
                     }
                }




   }
   
 //..................................thinning() PROCESS.........................
 
 
 
   void thinning()
{
 //converting the 2d matrix....................................................
    for(int i=2;i<200-2;i++){
        for(int j=2;j<200-2;j++){
           newp[i][j]=newpixels1[i*200+j];//System.out.print((newp[i][j] ));
           }
        }



             //Thinning Process......................................................................

   for(int y=1;y<200-1;y++){
         for(int x=1;x<200-1;x++){

     if((((newp[y-1][x-1] & 0xff)^m1[0][0])|((newp[y-1][x] & 0xff)^m1[0][1])|((newp[y-1][x+1] & 0xff)^m1[0][2])|((newp[y][x-1] & 0xff)^m1[1][0])
                    |((newp[y][x] & 0xff)^m1[1][1])|((newp[y][x+1] & 0xff)^m1[1][2])|((newp[y+1][x-1] & 0xff)^m1[2][0])
                          |((newp[y+1][x] & 0xff)^m1[2][1])|((newp[y+1][x+1] & 0xff)^m1[2][2])) == 0)

                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


     if((((newp[y-1][x-1] & 0xff)^m2[0][0])|((newp[y-1][x] & 0xff)^m2[0][1])|((newp[y][x-1] & 0xff)^m2[1][0])
                    |((newp[y][x] & 0xff)^m2[1][1])|((newp[y][x+1] & 0xff)^m2[1][2])|((newp[y+1][x-1] & 0xff)^m2[2][0])
                          |((newp[y+1][x+1] & 0xff)^m2[2][2])) == 0)

                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


     if((((newp[y-1][x-1] & 0xff)^m3[0][0])|((newp[y-1][x+1] & 0xff)^m3[0][2])|((newp[y][x-1] & 0xff)^m3[1][0])
                    |((newp[y][x] & 0xff)^m3[1][1])|((newp[y][x+1] & 0xff)^m3[1][2])|((newp[y+1][x-1] & 0xff)^m3[2][0])
                          |((newp[y+1][x] & 0xff)^m3[2][1])) == 0)

                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

      if((((newp[y-1][x-1] & 0xff)^m4[0][0])|((newp[y-1][x] & 0xff)^m4[0][1])|((newp[y][x] & 0xff)^m4[1][1])
              |((newp[y][x+1] & 0xff)^m4[1][2])|((newp[y+1][x-1] & 0xff)^m4[2][0])|((newp[y+1][x] & 0xff)^m4[2][1])
                        |((newp[y+1][x+1] & 0xff)^m4[2][2])) == 0)
       // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

     if((((newp[y-1][x] & 0xff)^m5[0][1])|((newp[y-1][x+1] & 0xff)^m5[0][2])|((newp[y][x-1] & 0xff)^m5[1][0])
                    |((newp[y][x] & 0xff)^m5[1][1])|((newp[y+1][x-1] & 0xff)^m5[2][0])|((newp[y+1][x] & 0xff)^m5[2][1])
                          |((newp[y+1][x+1] & 0xff)^m5[2][2])) == 0)
      // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


      if((((newp[y-1][x] & 0xff)^m6[0][1])|((newp[y-1][x+1] & 0xff)^m6[0][2])|((newp[y][x-1] & 0xff)^m6[1][0])
                    |((newp[y][x] & 0xff)^m6[1][1])|((newp[y][x+1] & 0xff)^m6[1][2])|((newp[y+1][x-1] & 0xff)^m6[2][0])
                          |((newp[y+1][x+1] & 0xff)^m6[2][2])) == 0)
      // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


     if((((newp[y-1][x-1] & 0xff)^m7[0][0])|((newp[y-1][x] & 0xff)^m7[0][1])|((newp[y-1][x+1] & 0xff)^m7[0][2])
                    |((newp[y][x] & 0xff)^m7[1][1])|((newp[y][x+1] & 0xff)^m7[1][2])|((newp[y+1][x-1] & 0xff)^m7[2][0])
                          |((newp[y+1][x] & 0xff)^m7[2][1])) == 0)
      // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

     if((((newp[y-1][x-1] & 0xff)^m8[0][0])|((newp[y-1][x] & 0xff)^m8[0][1])|((newp[y-1][x+1] & 0xff)^m8[0][2])|((newp[y][x-1] & 0xff)^m8[1][0])
                    |((newp[y][x] & 0xff)^m8[1][1])|((newp[y][x+1] & 0xff)^m8[1][2])|((newp[y+1][x-1] & 0xff)^m8[2][0])
                          |((newp[y+1][x] & 0xff)^m8[2][1])|((newp[y+1][x+1] & 0xff)^m8[2][2])) == 0)
      // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


     if((((newp[y-1][x-1] & 0xff)^m9[0][0])|((newp[y-1][x] & 0xff)^m9[0][1])|((newp[y-1][x+1] & 0xff)^m9[0][2])|((newp[y][x-1] & 0xff)^m9[1][0])
                    |((newp[y][x] & 0xff)^m9[1][1])|((newp[y][x+1] & 0xff)^m9[1][2])|((newp[y+1][x-1] & 0xff)^m9[2][0])
                          |((newp[y+1][x] & 0xff)^m9[2][1])|((newp[y+1][x+1] & 0xff)^m9[2][2])) == 0)
    // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

    if((((newp[y-1][x-1] & 0xff)^m10[0][0])|((newp[y-1][x+1] & 0xff)^m10[0][2])|((newp[y][x-1] & 0xff)^m10[1][0])
                    |((newp[y][x] & 0xff)^m10[1][1])|((newp[y][x+1] & 0xff)^m10[1][2])|((newp[y+1][x] & 0xff)^m10[2][1])
                              |((newp[y+1][x+1] & 0xff)^m10[2][2])) == 0)
     // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

     if((((newp[y-1][x-1] & 0xff)^m11[0][0])|((newp[y-1][x] & 0xff)^m11[0][1])|((newp[y][x] & 0xff)^m11[1][1])
                       |((newp[y][x+1] &0xff)^m11[1][2])|((newp[y+1][x-1] & 0xff)^m11[2][0])
                             |((newp[y+1][x] & 0xff)^m11[2][1])|((newp[y+1][x+1] & 0xff)^m11[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
      if((((newp[y-1][x-1] & 0xff)^m12[0][0])|((newp[y-1][x] & 0xff)^m12[0][1])|((newp[y-1][x+1] & 0xff)^m12[0][2])|((newp[y][x-1] & 0xff)^m12[1][0])
                    |((newp[y][x] & 0xff)^m12[1][1])|((newp[y+1][x] & 0xff)^m12[2][1])
                            |((newp[y+1][x+1] & 0xff)^m12[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

     if((((newp[y-1][x-1] & 0xff)^m13[0][0])|((newp[y-1][x] & 0xff)^m13[0][1])|((newp[y][x-1] & 0xff)^m13[1][0])
                    |((newp[y][x] & 0xff)^m13[1][1])|((newp[y][x+1] & 0xff)^m13[1][2])|((newp[y+1][x-1] & 0xff)^m13[2][0])
                          |((newp[y+1][x+1] & 0xff)^m13[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

     if((((newp[y-1][x] & 0xff)^m14[0][1])|((newp[y-1][x+1] & 0xff)^m14[0][2])|((newp[y][x-1] & 0xff)^m14[1][0])
                    |((newp[y][x] & 0xff)^m14[1][1])|((newp[y+1][x-1] & 0xff)^m14[2][0])
                          |((newp[y+1][x] & 0xff)^m14[2][1])|((newp[y+1][x+1] & 0xff)^m14[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

    if((((newp[y-1][x-1] & 0xff)^m15[0][0])|((newp[y-1][x+1] & 0xff)^m15[0][2])|((newp[y][x-1] & 0xff)^m15[1][0])
                    |((newp[y][x] & 0xff)^m15[1][1])|((newp[y][x+1] & 0xff)^m15[1][2])|((newp[y+1][x-1] & 0xff)^m15[2][0])
                          |((newp[y+1][x] & 0xff)^m15[2][1])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

    if((((newp[y-1][x-1] & 0xff)^m16[0][0])|((newp[y-1][x] & 0xff)^m16[0][1])|((newp[y-1][x+1] & 0xff)^m16[0][2])|((newp[y][x-1] & 0xff)^m16[1][0])
                    |((newp[y][x] & 0xff)^m16[1][1])|((newp[y][x+1] & 0xff)^m16[1][2])|((newp[y+1][x-1] & 0xff)^m16[2][0])
                          |((newp[y+1][x] & 0xff)^m16[2][1])|((newp[y+1][x+1] & 0xff)^m16[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

    if((((newp[y-1][x-1] & 0xff)^m17[0][0])|((newp[y-1][x] & 0xff)^m17[0][1])|((newp[y-1][x+1] & 0xff)^m17[0][2])|((newp[y][x-1] & 0xff)^m17[1][0])
                    |((newp[y][x] & 0xff)^m17[1][1])|((newp[y][x+1] & 0xff)^m17[1][2])|((newp[y+1][x-1] & 0xff)^m17[2][0])
                          |((newp[y+1][x] & 0xff)^m17[2][1])|((newp[y+1][x+1] & 0xff)^m17[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

   if((((newp[y-1][x] & 0xff)^m18[0][1])|((newp[y-1][x+1] & 0xff)^m18[0][2])|((newp[y][x-1] & 0xff)^m18[1][0])
                    |((newp[y][x] & 0xff)^m18[1][1])|((newp[y][x+1] & 0xff)^m18[1][2])|((newp[y+1][x-1] & 0xff)^m18[2][0])
                          |((newp[y+1][x+1] & 0xff)^m18[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

    if((((newp[y-1][x-1] & 0xff)^m19[0][0])|((newp[y-1][x+1] & 0xff)^m19[0][2])|((newp[y][x-1] & 0xff)^m19[1][0])
                    |((newp[y][x] & 0xff)^m19[1][1])|((newp[y][x+1] & 0xff)^m19[1][2])
                          |((newp[y+1][x] & 0xff)^m19[2][1])|((newp[y+1][x+1] & 0xff)^m19[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

   if((((newp[y-1][x-1] & 0xff)^m20[0][0])|((newp[y-1][x] & 0xff)^m20[0][1])|((newp[y-1][x+1] & 0xff)^m20[0][2])
                    |((newp[y][x] & 0xff)^m20[1][1])|((newp[y][x+1] & 0xff)^m20[1][2])|((newp[y+1][x-1] & 0xff)^m20[2][0])
                          |((newp[y+1][x] & 0xff)^m20[2][1])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


    if((((newp[y-1][x-1] & 0xff)^m21[0][0])|((newp[y-1][x] & 0xff)^m21[0][1])|((newp[y-1][x+1] & 0xff)^m21[0][2])
                       |((newp[y][x-1] & 0xff)^m21[1][0])|((newp[y][x] & 0xff)^m21[1][1])
                          |((newp[y+1][x] & 0xff)^m21[2][1])|((newp[y+1][x+1] & 0xff)^m21[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}




//For Thinning Process for the Diagonal Pixels.........................................

    if((((newp[y-1][x] & 0xff)^d1[0][1])|((newp[y-1][x+1] & 0xff)^d1[0][2])|((newp[y][x-1] & 0xff)^d1[1][0])
                    |((newp[y][x] & 0xff)^d1[1][1])|((newp[y][x+1] & 0xff)^d1[1][2])
                          |((newp[y+1][x] & 0xff)^d1[2][1])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

   if((((newp[y-1][x] & 0xff)^d2[0][1])|((newp[y][x-1] & 0xff)^d2[1][0])
                    |((newp[y][x] & 0xff)^d2[1][1])|((newp[y][x+1] & 0xff)^d2[1][2])|((newp[y+1][x-1] & 0xff)^d2[2][0])
                          |((newp[y+1][x] & 0xff)^d2[2][1])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

  if((((newp[y-1][x-1] & 0xff)^d3[0][0])|((newp[y-1][x] & 0xff)^d3[0][1])|((newp[y][x-1] & 0xff)^d3[1][0])
                    |((newp[y][x] & 0xff)^d3[1][1])|((newp[y][x+1] & 0xff)^d3[1][2])
                          |((newp[y+1][x] & 0xff)^d3[2][1])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


   if((((newp[y-1][x] & 0xff)^d4[0][1])|((newp[y][x-1] & 0xff)^d4[1][0])
                    |((newp[y][x] & 0xff)^d4[1][1])|((newp[y][x+1] & 0xff)^d4[1][2])|((newp[y+1][x-1] & 0xff)^d4[2][0])
                          |((newp[y+1][x+1] & 0xff)^d4[2][2])) == 0)
        // System.out.print(x);
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

         }
      }

      //Enhanced Thinning Process......................................................................


    int c=0;
   for(int y=1;y<200-1;y++){
         for(int x=1;x<200-1;x++){
          if((newp[y][x] & 0xff)==0)
            {
              if((newp[y-1][x] & 0xff)==0)c++;
               if((newp[y+1][x] & 0xff)==0)c++;
                if((newp[y][x-1] & 0xff)==0)c++;
                 if((newp[y][x+1] & 0xff)==0)c++;
                  if(c>2)
                  {int n=255;newp[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
                  c=0;
             }

        }
   }
   

  }

  // .................................FINDING THE FEATURE POINTS...................
  
  

  void feature(String s)
{
//Taking the FEATURE matrix....................................................
      int  p=0;
    for(int i=0;i<200;i++){
        for(int j=0;j<200;j++){
           feature[i][j] = newp[i][j];
           p++;
           }
         }

    for(int y=2;y<200-2;y++){
           for(int x=2;x<200-2;x++){


 if((((newp[y-1][x-1] & 0xff)^b1[0][0])|((newp[y-1][x] & 0xff)^b1[0][1])|((newp[y-1][x+1] & 0xff)^b1[0][2])|((newp[y][x-1] & 0xff)^b1[1][0])
                    |((newp[y][x] & 0xff)^b1[1][1])|((newp[y][x+1] & 0xff)^b1[1][2])|((newp[y+1][x-1] & 0xff)^b1[2][0])
                          |((newp[y+1][x] & 0xff)^b1[2][1])|((newp[y+1][x+1] & 0xff)^b1[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

   else if((((newp[y-1][x-1] & 0xff)^b2[0][0])|((newp[y-1][x] & 0xff)^b2[0][1])|((newp[y-1][x+1] & 0xff)^b2[0][2])|((newp[y][x-1] & 0xff)^b2[1][0])
                    |((newp[y][x] & 0xff)^b2[1][1])|((newp[y][x+1] & 0xff)^b2[1][2])|((newp[y+1][x-1] & 0xff)^b2[2][0])
                          |((newp[y+1][x] & 0xff)^b2[2][1])|((newp[y+1][x+1] & 0xff)^b2[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

   else if((((newp[y-1][x-1] & 0xff)^b3[0][0])|((newp[y-1][x] & 0xff)^b3[0][1])|((newp[y-1][x+1] & 0xff)^b3[0][2])|((newp[y][x-1] & 0xff)^b3[1][0])
                    |((newp[y][x] & 0xff)^b3[1][1])|((newp[y][x+1] & 0xff)^b3[1][2])|((newp[y+1][x-1] & 0xff)^b3[2][0])
                          |((newp[y+1][x] & 0xff)^b3[2][1])|((newp[y+1][x+1] & 0xff)^b3[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

  else  if((((newp[y-1][x-1] & 0xff)^b4[0][0])|((newp[y-1][x] & 0xff)^b4[0][1])|((newp[y-1][x+1] & 0xff)^b4[0][2])|((newp[y][x-1] & 0xff)^b4[1][0])
                    |((newp[y][x] & 0xff)^b4[1][1])|((newp[y][x+1] & 0xff)^b4[1][2])|((newp[y+1][x-1] & 0xff)^b4[2][0])
                          |((newp[y+1][x] & 0xff)^b4[2][1])|((newp[y+1][x+1] & 0xff)^b4[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

else   if((((newp[y-1][x-1] & 0xff)^b5[0][0])|((newp[y-1][x] & 0xff)^b5[0][1])|((newp[y-1][x+1] & 0xff)^b5[0][2])|((newp[y][x-1] & 0xff)^b5[1][0])
                    |((newp[y][x] & 0xff)^b5[1][1])|((newp[y][x+1] & 0xff)^b5[1][2])|((newp[y+1][x-1] & 0xff)^b5[2][0])
                          |((newp[y+1][x] & 0xff)^b5[2][1])|((newp[y+1][x+1] & 0xff)^b5[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

  else  if((((newp[y-1][x-1] & 0xff)^b6[0][0])|((newp[y-1][x] & 0xff)^b6[0][1])|((newp[y][x-1] & 0xff)^b6[1][0])
                    |((newp[y][x] & 0xff)^b6[1][1])|((newp[y+1][x-1] & 0xff)^b6[2][0])
                          |((newp[y+1][x] & 0xff)^b6[2][1])|((newp[y+1][x+1] & 0xff)^b6[1][2])) == 0)

          {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

  else if((((newp[y-1][x-1] & 0xff)^b7[0][0])|((newp[y-1][x] & 0xff)^b7[0][1])|((newp[y-1][x+1] & 0xff)^b7[0][2])|((newp[y][x-1] & 0xff)^b7[1][0])
                    |((newp[y][x] & 0xff)^b7[1][1])|((newp[y][x+1] & 0xff)^b7[1][2])|((newp[y+1][x-1] & 0xff)^b7[2][0])
                          |((newp[y+1][x] & 0xff)^b7[2][1])|((newp[y+1][x+1] & 0xff)^b7[2][2])) == 0)

          {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b8[0][0])|((newp[y-1][x] & 0xff)^b8[0][1])|((newp[y-1][x+1] & 0xff)^b8[0][2])|((newp[y][x-1] & 0xff)^b8[1][0])
                    |((newp[y][x] & 0xff)^b8[1][1])|((newp[y][x+1] & 0xff)^b8[1][2])|((newp[y+1][x-1] & 0xff)^b8[2][0])
                          |((newp[y+1][x] & 0xff)^b8[2][1])|((newp[y+1][x+1] & 0xff)^b8[2][2])) == 0)

          {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b9[0][0])|((newp[y-1][x] & 0xff)^b9[0][1])|((newp[y-1][x+1] & 0xff)^b9[0][2])|((newp[y][x-1] & 0xff)^b9[1][0])
                    |((newp[y][x] & 0xff)^b9[1][1])|((newp[y][x+1] & 0xff)^b9[1][2])|((newp[y+1][x-1] & 0xff)^b9[2][0])
                          |((newp[y+1][x] & 0xff)^b9[2][1])|((newp[y+1][x+1] & 0xff)^b9[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b10[0][0])|((newp[y-1][x] & 0xff)^b10[0][1])|((newp[y-1][x+1] & 0xff)^b10[0][2])|((newp[y][x-1] & 0xff)^b10[1][0])
                    |((newp[y][x] & 0xff)^b10[1][1])|((newp[y][x+1] & 0xff)^b10[1][2])|((newp[y+1][x-1] & 0xff)^b10[2][0])
                          |((newp[y+1][x] & 0xff)^b10[2][1])|((newp[y+1][x+1] & 0xff)^b10[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b11[0][0])|((newp[y-1][x] & 0xff)^b11[0][1])|((newp[y-1][x+1] & 0xff)^b11[0][2])|((newp[y][x-1] & 0xff)^b11[1][0])
                    |((newp[y][x] & 0xff)^b11[1][1])|((newp[y][x+1] & 0xff)^b11[1][2])|((newp[y+1][x-1] & 0xff)^b11[2][0])
                          |((newp[y+1][x] & 0xff)^b11[2][1])|((newp[y+1][x+1] & 0xff)^b11[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

 else if((((newp[y-1][x-1] & 0xff)^b12[0][0])|((newp[y-1][x] & 0xff)^b12[0][1])|((newp[y-1][x+1] & 0xff)^b12[0][2])|((newp[y][x-1] & 0xff)^b12[1][0])
                    |((newp[y][x] & 0xff)^b12[1][1])|((newp[y][x+1] & 0xff)^b12[1][2])|((newp[y+1][x-1] & 0xff)^b12[2][0])
                          |((newp[y+1][x] & 0xff)^b12[2][1])|((newp[y+1][x+1] & 0xff)^b12[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
 else if((((newp[y-1][x-1] & 0xff)^b13[0][0])|((newp[y-1][x] & 0xff)^b13[0][1])|((newp[y-1][x+1] & 0xff)^b13[0][2])|((newp[y][x-1] & 0xff)^b13[1][0])
                    |((newp[y][x] & 0xff)^b13[1][1])|((newp[y][x+1] & 0xff)^b13[1][2])|((newp[y+1][x-1] & 0xff)^b13[2][0])
                          |((newp[y+1][x] & 0xff)^b13[2][1])|((newp[y+1][x+1] & 0xff)^b13[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
 else if((((newp[y-1][x-1] & 0xff)^b14[0][0])|((newp[y-1][x] & 0xff)^b14[0][1])|((newp[y-1][x+1] & 0xff)^b14[0][2])|((newp[y][x-1] & 0xff)^b14[1][0])
                    |((newp[y][x] & 0xff)^b14[1][1])|((newp[y][x+1] & 0xff)^b14[1][2])|((newp[y+1][x-1] & 0xff)^b14[2][0])
                          |((newp[y+1][x] & 0xff)^b14[2][1])|((newp[y+1][x+1] & 0xff)^b14[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
else if((((newp[y-1][x-1] & 0xff)^b15[0][0])|((newp[y-1][x] & 0xff)^b15[0][1])|((newp[y-1][x+1] & 0xff)^b15[0][2])|((newp[y][x-1] & 0xff)^b15[1][0])
                    |((newp[y][x] & 0xff)^b15[1][1])|((newp[y][x+1] & 0xff)^b15[1][2])|((newp[y+1][x-1] & 0xff)^b15[2][0])
                          |((newp[y+1][x] & 0xff)^b15[2][1])|((newp[y+1][x+1] & 0xff)^b15[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

 else if((((newp[y-1][x-1] & 0xff)^b16[0][0])|((newp[y-1][x] & 0xff)^b16[0][1])|((newp[y-1][x+1] & 0xff)^b16[0][2])|((newp[y][x-1] & 0xff)^b16[1][0])
                    |((newp[y][x] & 0xff)^b16[1][1])|((newp[y][x+1] & 0xff)^b16[1][2])|((newp[y+1][x-1] & 0xff)^b16[2][0])
                          |((newp[y+1][x] & 0xff)^b16[2][1])|((newp[y+1][x+1] & 0xff)^b16[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
 else if((((newp[y-1][x-1] & 0xff)^b17[0][0])|((newp[y-1][x] & 0xff)^b17[0][1])|((newp[y-1][x+1] & 0xff)^b17[0][2])|((newp[y][x-1] & 0xff)^b17[1][0])
                    |((newp[y][x] & 0xff)^b17[1][1])|((newp[y][x+1] & 0xff)^b17[1][2])|((newp[y+1][x-1] & 0xff)^b17[2][0])
                          |((newp[y+1][x] & 0xff)^b17[2][1])|((newp[y+1][x+1] & 0xff)^b17[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b18[0][0])|((newp[y-1][x] & 0xff)^b18[0][1])|((newp[y-1][x+1] & 0xff)^b18[0][2])|((newp[y][x-1] & 0xff)^b18[1][0])
                    |((newp[y][x] & 0xff)^b18[1][1])|((newp[y][x+1] & 0xff)^b18[1][2])|((newp[y+1][x-1] & 0xff)^b18[2][0])
                          |((newp[y+1][x] & 0xff)^b18[2][1])|((newp[y+1][x+1] & 0xff)^b18[2][2])) == 0)

          {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b19[0][0])|((newp[y-1][x] & 0xff)^b19[0][1])|((newp[y-1][x+1] & 0xff)^b19[0][2])|((newp[y][x-1] & 0xff)^b19[1][0])
                    |((newp[y][x] & 0xff)^b19[1][1])|((newp[y][x+1] & 0xff)^b19[1][2])|((newp[y+1][x-1] & 0xff)^b19[2][0])
                          |((newp[y+1][x] & 0xff)^b19[2][1])|((newp[y+1][x+1] & 0xff)^b19[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

else if((((newp[y-1][x-1] & 0xff)^b20[0][0])|((newp[y-1][x] & 0xff)^b20[0][1])|((newp[y-1][x+1] & 0xff)^b20[0][2])|((newp[y][x-1] & 0xff)^b20[1][0])
                    |((newp[y][x] & 0xff)^b20[1][1])|((newp[y][x+1] & 0xff)^b20[1][2])|((newp[y+1][x-1] & 0xff)^b20[2][0])
                          |((newp[y+1][x] & 0xff)^b20[2][1])|((newp[y+1][x+1] & 0xff)^b20[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
else if((((newp[y-1][x-1] & 0xff)^b21[0][0])|((newp[y-1][x] & 0xff)^b21[0][1])|((newp[y-1][x+1] & 0xff)^b21[0][2])|((newp[y][x-1] & 0xff)^b21[1][0])
                    |((newp[y][x] & 0xff)^b21[1][1])|((newp[y][x+1] & 0xff)^b21[1][2])|((newp[y+1][x-1] & 0xff)^b21[2][0])
                          |((newp[y+1][x] & 0xff)^b21[2][1])|((newp[y+1][x+1] & 0xff)^b21[2][2])) == 0)

          {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
else if((((newp[y-1][x-1] & 0xff)^b22[0][0])|((newp[y-1][x] & 0xff)^b22[0][1])|((newp[y-1][x+1] & 0xff)^b22[0][2])|((newp[y][x-1] & 0xff)^b22[1][0])
                    |((newp[y][x] & 0xff)^b22[1][1])|((newp[y][x+1] & 0xff)^b22[1][2])|((newp[y+1][x-1] & 0xff)^b22[2][0])
                          |((newp[y+1][x] & 0xff)^b22[2][1])|((newp[y+1][x+1] & 0xff)^b22[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
 else if((((newp[y-1][x-1] & 0xff)^b23[0][0])|((newp[y-1][x] & 0xff)^b23[0][1])|((newp[y-1][x+1] & 0xff)^b23[0][2])|((newp[y][x-1] & 0xff)^b23[1][0])
                    |((newp[y][x] & 0xff)^b23[1][1])|((newp[y][x+1] & 0xff)^b23[1][2])|((newp[y+1][x-1] & 0xff)^b23[2][0])
                          |((newp[y+1][x] & 0xff)^b23[2][1])|((newp[y+1][x+1] & 0xff)^b23[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}
  else if((((newp[y-1][x-1] & 0xff)^b24[0][0])|((newp[y-1][x] & 0xff)^b24[0][1])|((newp[y-1][x+1] & 0xff)^b24[0][2])|((newp[y][x-1] & 0xff)^b24[1][0])
                    |((newp[y][x] & 0xff)^b24[1][1])|((newp[y][x+1] & 0xff)^b24[1][2])|((newp[y+1][x-1] & 0xff)^b24[2][0])
                          |((newp[y+1][x] & 0xff)^b24[2][1])|((newp[y+1][x+1] & 0xff)^b24[2][2])) == 0)

           {int n=0;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}


       else       {int n=255;feature[y][x]=(0xff000000 | (n << 16) |( n << 8) | n);}

    }
   }


                        try
                          {
                            File outfile1=new File(s+"_FEATURE_POINT.dat");

                               FileWriter ous1 = new FileWriter(outfile1);
                               BufferedWriter bw1 = new BufferedWriter(ous1);
                               bw1.write("row(Y)"+" \tcol(X)"+"\t"+"\t\n");
                           for(row =2 ; row <200-2 ; row++){
                                 for(col =2 ; col <200-2; col++){
                                         if((feature[row][col] & 0xff )== 0) {
                                                count++;
                                                bw1.write(" \t"+row+" \t"+col+"\t"+(edgedir[row][col])+"\t\n");

                                         }

                                      }
                               }
                               bw1.close();
                          }

                   catch ( IOException e ){ System.out.println("Error");}
                          
                          Fxpoint = new int[count+1];
                          Fypoint = new int[count+1];
                          Fangle = new float[count+1];
                          EUdist = new float[count+1][count+1];
                          Fxy    = new int[count+1];
                   //  Declaring A Array For Sorting All Points................................

                          FXYcost = new int [count][5];
                          for(int i=0;i<count;i++){
                            for(int j=0;j<5;j++){
                            FXYcost[i][j]=0;
                            }
                          }
                      //Storing All Point In Different ...........................
                      
                             Fxpoint[0]=107;
                             Fypoint[0]=202;
                             Fangle[0]=edgedir[202][107];
                              int I=1;
                          for(row =2 ; row <200-2 ; row++){
                                 for(col =2 ; col <200-2; col++){
                                        if((feature[row][col] & 0xff )== 0) {
                                           Fxpoint[I]=col;
                                           Fypoint[I]=row;
                                           Fangle[I]=edgedir[row][col];
                                           I++;
                                         }
                                    }
                                  }
                 // Calculating The Euclidian Distance..........................
                 
                        for(int i=0;i<count+1;i++){

                          for(int j=i+1;j<count+1;j++){

                            if(i==j)
                                EUdist[i][j]=0;
                            else{
                              int temp1 = (int)Math.pow((Fxpoint[i]-Fxpoint[j]),2.0);
                              int temp2 = (int)Math.pow((Fypoint[i]-Fypoint[j]),2.0);
                              EUdist[i][j]=(int)(Math.sqrt(temp1+temp2));
                              EUdist[j][i]=(int)(Math.sqrt(temp1+temp2));
                            }
                           }
                        }

                 //Writting The Distances In The File..........................
                 
                        try
                          {
                            File outfile2=new File(s+"_DIST.dat");

                               FileWriter ous2 = new FileWriter(outfile2);
                               BufferedWriter bw2 = new BufferedWriter(ous2);
                               
                               for(int i=0;i<count+1;i++){
                                for(int j=0;j<count+1;j++){
                                 bw2.write(EUdist[i][j]+"\t");
                                }
                                 bw2.write("\n");
                              }
                               bw2.close();
                          }

                   catch ( IOException e ){ System.out.println("Error");}


                   //Finding Out The Sortest distance Of the Points.....................

                    int k=0,t=0,c=0;
                    int[] tp=new int[count+2];
                      tp[0]=0;

                    while( (c<count)){

                         int small = 9999;
                           for(int i=k, j=i;j<count+1;j++){

                               if(i!=j){
                                 if(small > EUdist[i][j]){
                                    small =(int) EUdist[i][j];
                                     t=j;
                                  }
                                }
                             }
                             FXYcost[c][0]=Fxpoint[k];FXYcost[c][2]=Fxpoint[t];
                             FXYcost[c][1]=Fypoint[k];FXYcost[c][3]=Fypoint[t];
                             FXYcost[c][4]=small;tp[c+1]=t;

                               Fxy[k]=1;
                                k=t;

                            if(((t== count)) ){ Fxy[t]=1; k=0;}
                            
                            else if((t!=count)&&(EUdist[k][t+1]==9999)){Fxy[t]=1;k=0;}
                            
                            for(int i=0;i<count+1;i++){
                                    EUdist[i][t]=9999;
                                  }
                      c++;

                    }
//..............................................................................................................................
                       try
                          {
                            File outfile3=new File(s+"_TREE.dat");

                               FileWriter ous3 = new FileWriter(outfile3);
                               BufferedWriter bw3 = new BufferedWriter(ous3);

                               for(int i=0;i<count;i++){
                                  for(int j=0;j< 5;j++){
                                       bw3.write(FXYcost[i][j]+"\t");
                                      }
                                  bw3.write("\n");
                                    }
                              bw3.close();
                          }

                         catch ( IOException e ){ System.out.println("Error");}
  }


 //...................................FORMING THE TREE...........................
 
 
   void tree()
    {
       int c=0,k=0;
       int[][] level1 = new int[count][2];
       levels = new int[count][2];
       for(int i=0;i<count;i++)
         if((FXYcost[i][0]==FXYcost[0][0]) && (FXYcost[i][1]==FXYcost[0][1]))
          c++;
       value=c*0.4f;
       height[0] = new int[2*c+1];
       height[0][0] = c;
       
       int t=1;
       for(int i=0;i<count;i++)
        if((FXYcost[i][0]==FXYcost[0][0]) && (FXYcost[i][1]==FXYcost[0][1])){
          levels[k][0]=FXYcost[i][2];
          levels[k][1]=FXYcost[i][3];
          k++;
          height[0][t] = FXYcost[i][2];
          t++;
          height[0][t] = FXYcost[i][3];
          t++;
          }

        c=0;
        
        for(int i=0;i<k;i++){
          for(int j=0;j<count;j++)
          if((levels[i][0]==FXYcost[j][0]) && (levels[i][1]==FXYcost[j][1]))
           c++;
         }

      value= value + (c*0.3f);
      height[1] = new int[2*c+1];
      height[1][0] = c;

       int l=0;t=1; 
       for(int i=0;i<k;i++){
          for(int j=0;j<count;j++)
            if((levels[i][0]==FXYcost[j][0]) && (levels[i][1]==FXYcost[j][1])){
             level1[l][0]=FXYcost[j][2];
             level1[l][1]=FXYcost[j][3];
             l++;
             height[1][t] = FXYcost[j][2];
             t++;
             height[1][t] = FXYcost[j][3];
             t++;
            }
         }


     c=0;
     for(int i=0;i<k;i++){
       for(int j=0;j<count;j++)
          if((level1[i][0]==FXYcost[j][0]) && (level1[i][1]==FXYcost[j][1]))
           c++;
         }

     value = value + (c*0.1f);
      height[2] = new int[2*c+1];
      height[2][0] = c;
     
     l=0; t=1;
       for(int i=0;i<k;i++){
          for(int j=0;j<count;j++)
            if((level1[i][0]==FXYcost[j][0]) && (level1[i][1]==FXYcost[j][1])){
             levels[l][0]=FXYcost[j][2];
             levels[l][1]=FXYcost[j][3];
             l++;
             height[2][t] = FXYcost[j][2];
             t++;
             height[2][t] = FXYcost[j][3];
             t++;
            }
         }

      c=0;
      for(int i=0;i<l;i++){
       for(int j=0;j<count;j++)
          if((levels[i][0]==FXYcost[j][0]) && (levels[i][1]==FXYcost[j][1]))
           c++;
         }

      value = value + (c*0.1f);
      height[3] = new int[2*c+1];
      height[3][0] = c;

     k=0;t=1;
     for(int i=0;i<l;i++){
       for(int j=0;j<count;j++)
          if((levels[i][0]==FXYcost[j][0]) && (levels[i][1]==FXYcost[j][1])){
              level1[k][0]=FXYcost[j][2];
              level1[k][1]=FXYcost[j][3];
              k++;
              height[3][t] = FXYcost[j][2];
              t++;
              height[3][t] = FXYcost[j][3];
              t++;
           }      
        }

    c=0;
    for(int i=0;i<k;i++){
       for(int j=0;j<count;j++)
          if((level1[i][0]==FXYcost[j][0]) && (level1[i][1]==FXYcost[j][1]))
           c++;
         }

      value = value + (c*0.1f);
      height[4] = new int[2*c+1];
      height[4][0] = c;

       l=0; t=1;
       for(int i=0;i<k;i++){
          for(int j=0;j<count;j++)
            if((level1[i][0]==FXYcost[j][0]) && (level1[i][1]==FXYcost[j][1])){
             levels[l][0]=FXYcost[j][2];
             levels[l][1]=FXYcost[j][3];
             l++;
             height[4][t] = FXYcost[j][2];
             t++;
             height[4][t] = FXYcost[j][3];
             t++;
            }
         }

  for(int j=0;j<5;j++){
   for(int i=0;i<2*(height[j][0]);i++){
    System.out.print(height[j][i+1]+"\t");  
   }
    System.out.println();
   }

        System.out.println("VALUE: "+ value);
 }


 //..................................FINGER PRINT MATCHING PROCESS....................
 
 
  float match(graphpoint m)
   {
     int c=0,s=1,t=1;
     float match=0;
     if((m.FXYcost[0][0]==FXYcost[0][0]) && (m.FXYcost[0][1]==FXYcost[0][1])){
    for(int k=0;k<5;k++){
     for(int i=0;i<m.height[k][0];i++){
       for(int j=0;j<height[k][0];j++){
         if((m.height[k][s]==height[k][t]) && (m.height[k][s+1]==height[k][t+1])){
           c++; }
         t=t+2;
        }
        s=s+2;
        t=1;
      }
     s=1;t=1;
    switch(k)
    {
     case 0: match+=c*0.4f;
                break;
     case 1: match+=c*0.3f;
                break;
     case 2:
     case 3:
     case 4: match+=c*0.1f;
                break;
     default : break;
    }
    c=0;
    }
    
    return match;
   }

  else{
      return 0.0f;} 

  }
 }

