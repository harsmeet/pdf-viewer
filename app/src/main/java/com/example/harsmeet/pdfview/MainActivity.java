package com.example.harsmeet.pdfview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import java.util.List;

public class MainActivity extends Activity implements OnPageChangeListener,OnLoadCompleteListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "cloud.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

try {
    pdfView = (PDFView) findViewById(R.id.pdfView);
    displayFromAsset(SAMPLE_FILE);

}catch (Exception e){
    e.printStackTrace();
}
    }

    private void displayFromAsset(String assetFileName) {

        try {
            pdfFileName = assetFileName;
            pdfView.fromAsset(SAMPLE_FILE)
                    .defaultPage(pageNumber)
                    .enableSwipe(true)

                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }



    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}

//    PDFView pdfView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        TextView tv_header = findViewById(R.id.tv_header);
//        pdfView = (PDFView)findViewById(R.id.pdfView);
//
//        tv_header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                pdfView.fromAsset("cloud.pdf").load();
//
//            }
//        });
//
//
//    }}
