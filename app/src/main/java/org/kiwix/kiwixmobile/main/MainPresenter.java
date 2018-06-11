package org.kiwix.kiwixmobile.main;

import android.util.Log;

import org.kiwix.kiwixmobile.base.BasePresenter;
import org.kiwix.kiwixmobile.data.DataSource;
import org.kiwix.kiwixmobile.di.PerActivity;
import org.kiwix.kiwixmobile.library.entity.LibraryNetworkEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Presenter for {@link MainActivity}.
 */

@PerActivity
class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

  private DataSource dataSource;

  @Inject
  MainPresenter(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void showHome() {
    dataSource.getLanguageCategorizedBooks()
        .subscribe(new SingleObserver<List<LibraryNetworkEntity.Book>>() {
          @Override
          public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override
          public void onSuccess(List<LibraryNetworkEntity.Book> books) {
            view.addBooks(books);
          }

          @Override
          public void onError(Throwable e) {
            Log.d("MainPresenter", e.toString());
          }
        });
  }
}