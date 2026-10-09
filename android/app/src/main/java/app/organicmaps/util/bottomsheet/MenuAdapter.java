package app.organicmaps.util.bottomsheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import app.organicmaps.R;
import app.organicmaps.sdk.location.TrackRecorder;
import app.organicmaps.sdk.util.Config;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{
  private final ArrayList<MenuBottomSheetItem> dataSet;
  @Nullable
  private final MenuBottomSheetItem.OnClickListener onClickListener;

  public MenuAdapter(ArrayList<MenuBottomSheetItem> dataSet,
                     @Nullable MenuBottomSheetItem.OnClickListener onClickListener)
  {
    this.dataSet = dataSet;
    this.onClickListener = onClickListener;
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, final int position)
  {
    final MenuBottomSheetItem item = dataSet.get(position);

    // إخفاء زر التبرع وقسم "حول التطبيق" نهائياً من القائمة
    if (item.iconRes == R.drawable.ic_donate || item.titleRes == R.string.about)
    {
      viewHolder.itemView.setVisibility(View.GONE);
      viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
      return;
    }
    else
    {
      viewHolder.itemView.setVisibility(View.VISIBLE);
      viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
          RecyclerView.LayoutParams.MATCH_PARENT,
          RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    final ImageView iv = viewHolder.getIconImageView();
    iv.setImageResource(item.iconRes);
    viewHolder.getContainer().setOnClickListener((v) -> onMenuItemClick(item));
    viewHolder.getTitleTextView().setText(item.titleRes);
    TextView badge = viewHolder.getBadgeTextView();
    if (item.badgeCount > 0)
    {
      badge.setText(String.valueOf(item.badgeCount));
      badge.setVisibility(View.VISIBLE);
    }
    else
    {
      badge.setVisibility(View.GONE);
    }

    if (item.iconRes == R.drawable.ic_track_recording_off && TrackRecorder.nativeIsTrackRecordingEnabled())
    {
      iv.setImageResource(R.drawable.ic_track_recording_on);
      iv.setImageTintMode(null);
      viewHolder.getTitleTextView().setText(R.string.stop_track_recording);
      badge.setBackgroundResource(R.drawable.track_recorder_badge);
      badge.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getItemCount()
  {
    return dataSet.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder
  {
    private final LinearLayout container;
    private final ImageView iconImageView;
    private final TextView titleTextView;
    private final TextView badgeTextView;

    public ViewHolder(View view)
    {
      super(view);
      container = view.findViewById(R.id.bottom_sheet_menu_item);
      iconImageView = view.findViewById(R.id.bottom_sheet_menu_item_icon);
      titleTextView = view.findViewById(R.id.bottom_sheet_menu_item_text);
      badgeTextView = view.findViewById(R.id.bottom_sheet_menu_item_badge);
    }

    public ImageView getIconImageView()
    {
      return iconImageView;
    }

    public TextView getTitleTextView()
    {
      return titleTextView;
    }

    public TextView getBadgeTextView()
    {
      return badgeTextView;
    }

    public LinearLayout getContainer()
    {
      return container;
    }
  }
}
