/*
 * Copyright (C) 2014 The Android Open Source Project
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
package com.example.android.uamp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.uamp.R;
import com.example.android.uamp.utils.LollipopUtils;

public class MediaItemViewHolder {

    static final int STATE_INVALID = -1;
    static final int STATE_NONE = 0;
    static final int STATE_PLAYABLE = 1;
    static final int STATE_PAUSED = 2;
    static final int STATE_PLAYING = 3;

    private static ColorStateList sColorStatePlaying;
    private static ColorStateList sColorStateNotPlaying;

    ImageView mImageView;
    ImageView mPlaylist;
    TextView mTitleView;
    TextView mDescriptionView;

    static View setupView(Activity activity, View convertView, ViewGroup parent,
                                    MediaDescriptionCompat description, int state) {

        if (sColorStateNotPlaying == null || sColorStatePlaying == null) {
            initializeColorStateLists(activity);
        }

        MediaItemViewHolder holder;

        //get a reference to the view for pressing
/*        TextView pressView = (TextView)findViewById(R.id.press);
        //register if for context
        registerForContextMenu(pressView);*/


        Integer cachedState = STATE_INVALID;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.media_list_item, parent, false);
            holder = new MediaItemViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.play_eq);
            holder.mTitleView = (TextView) convertView.findViewById(R.id.title);
            holder.mDescriptionView = (TextView) convertView.findViewById(R.id.description);
            holder.mPlaylist=(ImageView) convertView.findViewById(R.id.addPlayList);
/*
            activity.registerForContextMenu(holder.mImageView);
            activity.registerForContextMenu(holder.mTitleView);
            activity.registerForContextMenu(holder.mDescriptionView);
            activity.registerForContextMenu(holder.mPlaylist);
*/

            convertView.setTag(holder);
        } else {
            holder = (MediaItemViewHolder) convertView.getTag();
            cachedState = (Integer) convertView.getTag(R.id.tag_mediaitem_state_cache);
        }

        holder.mTitleView.setText(description.getTitle());
        holder.mDescriptionView.setText(description.getSubtitle());

        /*activity.registerForContextMenu(holder.mImageView);
        activity.registerForContextMenu(holder.mTitleView);
        activity.registerForContextMenu(holder.mDescriptionView);
        activity.registerForContextMenu(holder.mPlaylist);*/

        // If the state of convertView is different, we need to adapt the view to the
        // new state.
        if (cachedState == null || cachedState != state) {
            switch (state) {
                case STATE_PLAYABLE:
                    holder.mImageView.setImageDrawable(
                            ActivityCompat.getDrawable(activity, R.drawable.ic_play_arrow_black_36dp));
                    LollipopUtils.setImageTintList(holder.mImageView, sColorStateNotPlaying);
                    holder.mImageView.setVisibility(View.VISIBLE);
                    holder.mPlaylist.setVisibility(View.GONE);
/*                    activity.registerForContextMenu(holder.mImageView);
                    activity.registerForContextMenu(holder.mTitleView);
                    activity.registerForContextMenu(holder.mDescriptionView);
                    activity.registerForContextMenu(holder.mPlaylist);*/

                    break;
                case STATE_PLAYING:
                    AnimationDrawable animation = (AnimationDrawable)
                            ActivityCompat.getDrawable(activity, R.drawable.ic_equalizer_white_36dp);
                    holder.mImageView.setImageDrawable(animation);
                    LollipopUtils.setImageTintList(holder.mImageView, sColorStatePlaying);
                    holder.mImageView.setVisibility(View.VISIBLE);
                    holder.mPlaylist.setVisibility(View.GONE);
                    animation.start();
                    break;
                case STATE_PAUSED:
                    holder.mImageView.setImageDrawable(
                            ActivityCompat.getDrawable(activity, R.drawable.ic_equalizer1_white_36dp));
                    LollipopUtils.setImageTintList(holder.mImageView, sColorStateNotPlaying);
                    holder.mImageView.setVisibility(View.VISIBLE);
                    holder.mPlaylist.setVisibility(View.GONE);
                    break;
                default:
                    holder.mImageView.setVisibility(View.GONE);
                    holder.mPlaylist.setVisibility(View.GONE);
            }
            convertView.setTag(R.id.tag_mediaitem_state_cache, state);
        }

        return convertView;
    }


/*    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Action 1");
        menu.add(0, v.getId(), 0, "Action 2");
    }*/


    static private void initializeColorStateLists(Context ctx) {
        sColorStateNotPlaying = ColorStateList.valueOf(ctx.getResources().getColor(
            R.color.media_item_icon_not_playing));
        sColorStatePlaying = ColorStateList.valueOf(ctx.getResources().getColor(
            R.color.media_item_icon_playing));
    }
}
