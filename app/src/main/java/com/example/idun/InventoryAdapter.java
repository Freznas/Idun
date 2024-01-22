//package com.example.idun;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class InventoryAdapter extends BaseExpandableListAdapter
//{
//
//    private final Context context;
//    private List<String> inventoryGroups = new ArrayList<>();
//    private Map<String, List <String>> inventoryItems = new HashMap<>();
//
//    public InventoryAdapter(Context context)
//    {
//        this.context=context;
//
//    }
//
//    @Override
//    public int getGroupCount()
//    {
//        return inventoryGroups.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition)
//    {
//        String group = inventoryGroups.get(groupPosition);
//
//        return inventoryItems.containsKey(group)? inventoryItems.get(group).size():0;
//    }
//
//    @Override
//    public Object getGroup(int groupPosition)
//    {
//        return inventoryGroups.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition)
//    {
//        String group =inventoryGroups.get(groupPosition);
//        return inventoryItems.containsKey(group)?inventoryItems.get(group).get(childPosition):"";
//    }
//
//    @Override
//    public long getGroupId(int groupPosition)
//    {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition)
//    {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds()
//    {
//        return true;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
//    {
//        View groupView = convertView != null ?convertView: LayoutInflater.from(context).inflate(R.layout.activity_inventory,parent,false);
//        TextView groupHeaderTextView = groupView.findViewById(R.id.elv_Inventory1);
//        groupHeaderTextView.setText(String.valueOf(getGroup(groupPosition)));
//
//        return groupView;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
//    {
//
//        View childView = convertView != null ? convertView: LayoutInflater.from(context).inflate(R.layout.activity_inventory,parent,false);
//
//        TextView childItemTextView = childView.findViewById(R.id.elv_Inventory1);
//        childItemTextView.setText(String.valueOf(getChild(groupPosition,childPosition)));
//        return childView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition)
//    {
//
//        return true;
//    }
//
//    public void setInventoryItems(List<String> inventoryGroups, Map<String, List<String>> inventoryItems)
//    {
//        this.inventoryGroups = inventoryGroups;
//        this.inventoryItems = inventoryItems;
//        notifyDataSetChanged();
//    }
//
//    public void setInventoryItems(@NotNull List<String> toMutableList)
//    {
//
//    }
//}
