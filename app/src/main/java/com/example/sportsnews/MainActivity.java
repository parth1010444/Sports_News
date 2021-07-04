package com.example.sportsnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<Information> information;
    InformationArrayAdoptor myAdoptor;
    ListView listView;

    static ArrayList<Bitmap> img;

    SharedPreferences sharedPreferences;

    ProgressDialog progress;


    DownloadTask task;
    boolean cricketnews = false;

    /////////////////////////////download image///////////////////
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            URL url = null;
            HttpURLConnection connection = null;

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_elements, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.news) {
            Log.i("yues", "okkh");
            if (cricketnews == true) {
                cricketnews = false;
                DownloadTask task1 = new DownloadTask();
                task1.execute();
            } else {
                cricketnews = true;
                DownloadTask task2 = new DownloadTask();
                task2.execute();

            }
        }

        return true;
    }

    public class DownloadTask extends AsyncTask<Void, Integer, String> {


        @Override
        protected void onCancelled() {
            super.onCancelled();

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress.setMessage("LOADING");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progress.setProgress(values[0]);

            if (information.size() != 0) {
                myAdoptor.notifyDataSetChanged();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("post execute", "success");

        }

        @Override
        protected String doInBackground(Void... voids) {
            NewsApiClient newsApiClient = new NewsApiClient("71ef4bd9332b405992683fc2c30feb5d");
            try {

                Log.i("post execute", "doinBackgor");
                information.clear();
                if (cricketnews == true) {

                    newsApiClient.getTopHeadlines(
                            new TopHeadlinesRequest.Builder()
                                    .q("cricket")
                                    .language("en")
                                    .country("in")
                                    .category("sports")
                                    .build(),
                            new NewsApiClient.ArticlesResponseCallback() {
                                @Override
                                public void onSuccess(ArticleResponse response) {

                                    Log.i("articles connection", "success");

                                    //accessing all articles


                                    int count = 0;

                                    Log.i("size", Integer.toString(response.getArticles().size()));
                                    for (int i = 0; i < response.getArticles().size(); i++) {
                                        try {


                                            Thread.sleep(500);
                                            publishProgress(count);

                                            count += 10;

                                            Information obj = new Information();

                                            obj.setTitle(response.getArticles().get(i).getTitle());
                                            obj.setDesrciption(response.getArticles().get(i).getDescription());
                                            obj.setAuthor(response.getArticles().get(i).getAuthor());
                                            obj.setDateTime(response.getArticles().get(i).getPublishedAt());
                                            obj.setUrl(response.getArticles().get(i).getUrl());


                                            DownloadImage task = new DownloadImage();

                                            Bitmap myImage;

                                            try {
                                                myImage = task.execute(response.getArticles().get(i).getUrlToImage()).get();
                                                obj.setImg(myImage);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            Log.i("Time", response.getArticles().get(i).getPublishedAt());
                                            information.add(obj);
                                            Log.i("objection addition ", "success");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.i("objection addition ", "error");
                                        }
                                    }

                                    Log.i("size2", String.valueOf(information.size()));

                                    getSupportActionBar().setTitle("Cricket News");

                                    invalidateOptionsMenu();

                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Log.i("articles connection", "failure");
                                    System.out.println(throwable.getMessage());
                                }
                            }
                    );
                    String success = "yes";
                    if (isDestroyed())
                        return success;


                } else {

                    newsApiClient.getTopHeadlines(
                            new TopHeadlinesRequest.Builder()
                                    .language("en")
                                    .country("in")
                                    .category("sports")
                                    .build(),
                            new NewsApiClient.ArticlesResponseCallback() {
                                @Override
                                public void onSuccess(ArticleResponse response) {

                                    Log.i("articles connection", "success");

                                    //accessing all articles


                                    int count = 0;
                                    Log.i("size", Integer.toString(response.getArticles().size()));
                                    for (int i = 0; i < response.getArticles().size(); i++) {
                                        try {

                                            Thread.sleep(1000);
                                            publishProgress(count);
                                            count += 10;

                                            if (isCancelled()) break;

                                            Information obj = new Information();

                                            obj.setTitle(response.getArticles().get(i).getTitle());
                                            obj.setDesrciption(response.getArticles().get(i).getDescription());
                                            obj.setAuthor(response.getArticles().get(i).getAuthor());
                                            obj.setDateTime(response.getArticles().get(i).getPublishedAt());
                                            obj.setUrl(response.getArticles().get(i).getUrl());
                                            DownloadImage task = new DownloadImage();

                                            Bitmap myImage;

                                            try {
                                                myImage = task.execute(response.getArticles().get(i).getUrlToImage()).get();
                                                obj.setImg(myImage);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            Log.i("Time", response.getArticles().get(i).getPublishedAt());
                                            information.add(obj);
                                            Log.i("objection addition ", "success");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.i("objection addition ", "error");
                                        }
                                    }

                                    Log.i("size2", String.valueOf(information.size()));

                                    getSupportActionBar().setTitle("Sports News");

                                    invalidateOptionsMenu();
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Log.i("articles connection", "failure");
                                    System.out.println(throwable.getMessage());
                                }
                            }
                    );
                    String success = "yes";
                    if (isDestroyed())
                        return success;

                }
            } catch (Exception e) {
                Log.i("URL", "Failure");
            }
            return null;
        }


    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();

//        myAdoptor.notifyDataSetChanged();
        progress.cancel();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.news);
        if (cricketnews == true) {
            item.setTitle("Sports News");
        } else {
            item.setTitle("Cricket News");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("Create", "Success");

        ///////////////iniatilisation////////////////

        listView = findViewById(R.id.listView);

        information = new ArrayList<Information>();

        progress = new ProgressDialog(this);

        myAdoptor = new InformationArrayAdoptor(this, R.layout.information_list_view, information);

        listView.setAdapter(myAdoptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), informationExpand.class);
                intent.putExtra("url", information.get(position).getUrl());
                startActivity(intent);


            }
        });


        ///////////////////////////Downloading Articles////////////////

        task = new DownloadTask();
        String s = "";

        try {
            s = task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (s != null) {
            Log.i("result due to get", s);
        }

    }
}