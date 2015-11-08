import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.*;
//import javax.imageio.ImageIO;



/*
<applet code="orientation1" width=1500 height=1500>
<param name =img value=my4.jpg>
</applet>
*/
public class orientation1 extends Applet {

/**
   *
   */
Image img ,img1, img2,img3,img4,img5;
int[] pixels; int[][] newp; int[][] feature; int[] newpixels; int[] newpixels1; int[] newpixels2;int[] hist;int[] histcdf;int[] histeq;
int[][] gx;int[][] gy;
Dimension d;
int w,iw,h,ih;
float imw,imh;

             int[][] gxmask = new int[3][3];        //Sobel mask in X direction
             int[][] gymask = new int[3][3];        //Sobel mask in Y direction
             float asr;                          //Aspect ratio.................
             int newpixel;                       //Sum pixel value for gussian
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

 public void init(){
  setBackground(Color.black);
d= getSize();
w= d.width;
h =d.height;
//Declare Sobel Mask for Gx...........................
          gxmask[0][0]=-1;gxmask[0][1]=0;gxmask[0][2]=-1;
          gxmask[1][0]=-2;gxmask[1][1]=0;gxmask[1][2]=-2;
          gxmask[2][0]=-1;gxmask[2][1]=0;gxmask[2][2]=-1;

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

try{
img = getImage(getDocumentBase(),getParameter("img"));
MediaTracker t =new MediaTracker(this);
t.addImage(img,0);
t.waitForID(0);
iw=img.getWidth(null);System.out.println(" iw" +iw);
ih=img.getHeight(null);
pixels = new int[iw * ih];
hist = new int[256];
histcdf = new int[256];
histeq = new int[256];
newpixels = new int[iw * ih];newpixels1 = new int[iw * ih];newpixels2 = new int[iw * ih];
edgedir = new float[ih][iw];
gradient = new float[ih][iw];
gx = new int[ih][iw];
gy = new int[ih][iw];
newp = new int[ih][iw];
feature= new int[ih][iw];
 Diffx = new float[ih][iw];
 Diffy = new float[ih][iw];

PixelGrabber pg = new PixelGrabber(img, 0, 0, iw, ih, pixels, 0, iw);
    pg.grabPixels();
}
catch(InterruptedException e){System.out.println ("Interrupted"); return;}

//Gaussian Blur To Denoise ....................
          for(row =2; row<ih-2; row++){
               for(col=2 ; col<iw-2; col++){
                    newpixel=0;b=0;
                   for(rowoffset=-2 ; rowoffset<=2 ;rowoffset++){
                       for(coloffset =-2; coloffset<=2 ;coloffset++){
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

                 img1= createImage(new MemoryImageSource(iw, ih, newpixels, 0, iw));

//Histogram Analysis.....................................................

for(int j=0; j<255; j++)
{
     hist[j]=0;  histcdf[j]=0;
}

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
for(int j=0; j<255; j++){
  if(histcdf[j]<cdfmin)
   cdfmin=histcdf[j];}
int temp=((iw*ih)-cdfmin);
for(int j=0; j<255; j++)
{
   histeq[j]=(((histcdf[j]-cdfmin)*255)/temp);
}

for(int i=0; i<iw*ih; i++)
{
  rgb=newpixels[i];
  b= rgb  & 0xff;
int  n =histeq[b];
  newpixels[i]=(0xff000000 | (n << 16) |( n << 8) | n);
}

 img5= createImage(new MemoryImageSource(iw, ih, newpixels, 0, iw));

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
                               /*thisangle =(float) ((Math.atan2(gx1,gy1)/(2*3.14))*180.0);

                                if(((thisangle<0)&&(thisangle>=-90)))
                                         newangle = thisangle+180;
                                else
                                      newangle = thisangle;
                                edgedir[row][col]=newangle;*/
                          }
                     }

                    // for local ridge orientation..................
                         for(row=1;row<ih-1;row+=3){
                            for(col =1 ; col <iw-1 ; col+=3){
                                 float vx=0,vy=0;
                                 for(int i=0;i<3;i++){
                                     for(int j=0;j<3;j++){
                                     vx += (2*(gx[row-1+i][col-1+j])*(gy[row-1+i][col-1+j]));
                                     vy += ((Math.pow((gx[row-1+i][col-1+j]),2.0))-(Math.pow((gy[row-1+i][col-1+j]),2.0)));
                                     }
                                 }

                                thisangle =(float) Math.toRadians(((Math.atan(vy/vx))*(0.5)));

                                //if(((thisangle<0)&&(thisangle>=-90)))
                                  //      newangle = thisangle+180;
                                //else
                                      newangle = thisangle;
                                edgedir[row][col]=newangle;//System.out.println(edgedir[row][col]);
                            }
                         }

                     for(row =4 ; row <ih-1 ; row+=9){
                        for(col =4 ; col <iw-1 ; col+=9){
                             //calculate the Angle Diff..................
                                    float temp1=0,temp2=0,temp3=0,temp4=0;
                                 for(int i=0;i<9;i+=3){

                                  temp1 +=  (Math.sin(2*(edgedir[row-3][col-3+i]))); //System.out.println("TEMP1"+temp1);
                                  temp2 +=  (Math.sin(2*(edgedir[row+3][col+3-i]))); System.out.println("TEMP2 "+temp2);
                                  temp3 +=  (Math.cos(2*(edgedir[row-3+i][col-3])));
                                  temp4 +=  (Math.cos(2*(edgedir[row+3-i][col+3]))); System.out.println("TEMP4 "+temp4);


                                 }
                                 System.out.println("TEMP1");
                                  Diffx[row][col]= temp2-temp1;System.out.println("Diffx  :" +Diffx[row][col]);
                                  Diffy[row][col]= temp4-temp3;//System.out.println("Diffy  :" +Diffy[row][col]);


                         }
                      }

                      /* try
                          {
                            File outfile2 = new File("graph1.txt");
                            //PrintStream out = new PrintStream(outfile2);
                            
                            //out.println("Helllllllllloooooooo");

                               FileWriter ous2 = new FileWriter(outfile2);
                               BufferedWriter bw2 = new BufferedWriter(ous2);
                               //bw2.write(" row(Y)"+" \tcol(X)"+" \trow(Y)"+" \tcol(X)"+"\tangle"+"\tcost\t\n");

                               for(int i=0;i<count+1;i++){
                                for(int j=0;j<count+1;j++){
                                 bw2.write(EUdist[i][j]+"\t");
                                }
                                 bw2.write("\n");
                              }

                               // bw2.write(Fypoint[0]+" \t"+Fxpoint[0]+" \t"+Fypoint[1]+" \t"+Fxpoint[1]+"\t"+EUdist[1][0]+"\t"+small+"\t\n");
                               bw2.close();
                          }

                   catch ( IOException e ){ System.out.println("Error");} */
                   
                   float small=1;int tx=0,ty=0;
                           for(row =4 ; row <ih-1 ; row+=9){
                                 for(col =4 ; col <iw-1 ; col+=9){
                                         if((Diffx[row][col] < 0) && (Diffy[row][col] < 0) ){
                                              if(small > Diffx[row][col]){
                                                   small = Diffx[row][col];
                                                    tx=col;ty=row;
                                                  }
                                                System.out.println("row "+row +" col "+col + "X Diff "+Diffx[row][col]);
                                         }

                                      }
                               }
                             System.out.println( "Smallest "+"row "+ty +" col "+tx + "X diff "+small);




               for(row =2; row<ih-2 ;row++){
                     for(col =2; col<iw-2;col++){
                         i=(row  *iw + col);
                         rgb= newpixels[i];
                         b = rgb & 0xff;
                      if(b>60)
                         b=255;
                      else
                         b=0;
                      newpixels1[i] = (0xff000000 | (b << 16) |(b << 8) | b);
                     }
                }




}
public void update(){}
public void start(){setVisible(true);}
public void paint(Graphics g)
{
//g.drawImage(img4, 0, 0, null);
g.drawImage(img5, 300, 0, null);
g.drawImage(img1, 600, 0, null);
//g.drawImage(img4, 0, 400, null);

}
}