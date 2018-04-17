/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * {@link ForecastAdapter} Hava tahminlerinin listesi RecyclerView üzerinde tutulacaktır.
 * {@link android.support.v7.widget.RecyclerView}
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private String[] mWeatherData;


    /*
     * Bir etkinlik ile arayüz oluşturmayı kolaylaştırmak için  tanımladığımız bir tıklama işleyicisi
     * Arayüz Kaynağı : ForecastAdapterOnClickHandler
     */
    private final ForecastAdapterOnClickHandler mClickHandler;


    /**
     * Tıklama iletilerini alan arayüz.
     */
    public interface ForecastAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }


    /**
     * Bu yapıcı metot tıklama olaylarını dinleyip arayüze gönderecektir.
     */
    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }




    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final TextView mWeatherTextView;


        public ForecastAdapterViewHolder(View view) {
            super(view);
            mWeatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);
            view.setOnClickListener(this);
        }


        /**
         * Tıklama durumunda çağırılacak metot.
         *
         * @param v Tıklanan view'i alacağımız parametre - Bir view parametresidir. Çünkü Listview içerisindedir.
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWeatherData[adapterPosition];
            mClickHandler.onClick(weatherForDay);
        }
    }

    /**
     * Bir viewholder oluşturulacağı zaman çağırılacak metot.
     *
     * @param viewGroup ViewHolder ' ın içerdiği viewGroup
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext(); // Şişirme işleminde alacak parametrelerden biri.
        int layoutIdForListItem = R.layout.forecast_list_item; // Şişirilecek düzen
        LayoutInflater inflater = LayoutInflater.from(context); // Şişirmeyi sağlayacak metot.
        boolean shouldAttachToParentImmediately = false; // Sabit değer.

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ForecastAdapterViewHolder(view); // Geriye bir viewholder döndürecektir.
    }

    /**
     * ViewHolder'ın içeriğinin güncelleceği kısımdır.
     *
     * @param forecastAdapterViewHolder İlgili veri. : ViewHolder ı güncellenecek çünkü
     * @param position                  Öğenin konumu  : Veri nerede sorusunun cevabı
     */
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        String weatherForThisDay = mWeatherData[position];
        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }


    // Kaç tane veri var ona göre recyclerview uzunluğu belirlenecek.
    @Override
    public int getItemCount() {
        if (null == mWeatherData) return 0;
        return mWeatherData.length;
    }

    /**
     * Önceden adapter oluşturulduyas yenisinin oluşturulması için gereklidir.
     *
     * @param weatherData Yeni hava durumu verisi için weatherData verisi.
     */
    public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
}