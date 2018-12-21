package sample.architecture_components.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import sample.architecture_components.dao.WordDao;
import sample.architecture_components.database.WordRoomDatabase;
import sample.architecture_components.entities.Word;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getInstance(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        new InsertWordAsyncTask(wordDao).execute(word);
    }

    private static class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao taskWordDao;

        public InsertWordAsyncTask(WordDao wordDao) {
            taskWordDao = wordDao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            taskWordDao.insert(words[0]);
            return null;
        }
    }

}
