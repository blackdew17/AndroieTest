package com.eskim.picturesample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eskim.picturesample.adapter.MainAdapter;
import com.eskim.picturesample.api.PictureInfo;
import com.eskim.picturesample.retrofit.ApiClient;
import com.eskim.picturesample.rxEventBus.RxEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import com.eskim.picturesample.R;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private CompositeDisposable disposable = new CompositeDisposable();

    private MainAdapter adapter = new MainAdapter();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_view)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        disposable.add(ApiClient.getApiClient().getPictureUrlList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> {
                    progressBar.setVisibility(View.VISIBLE);
                })
                .doOnTerminate(() -> {
                    progressBar.setVisibility(View.GONE);
                })
                .subscribe(result -> {
                    adapter.setItems((ArrayList<PictureInfo>) result);
                }, error -> {
                    Toast toast = Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT);
                    toast.show();
                })
        );

        disposable.add(
                RxEvent.getInstance()
                        .getObservable()
                        .subscribe(
                                object -> {
                                    if (object instanceof PictureInfo) {
                                        adapter.updateView((PictureInfo) object);
                                    }
                                },
                                error -> {
                                    Log.d("es.kim", "onError");
                                },
                                () -> {
                                    Log.d("es.kim", "onCompleted");
                                }
                        )
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void onClick(PictureInfo picture) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_PICTURE, picture);
        startActivity(intent);
    }
}