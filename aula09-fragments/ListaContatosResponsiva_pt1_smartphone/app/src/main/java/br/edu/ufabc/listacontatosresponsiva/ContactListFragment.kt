package br.edu.ufabc.listacontatosresponsiva

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufabc.listacontatosresponsiva.databinding.ContactListItemBinding
import br.edu.ufabc.listacontatosresponsiva.databinding.FragmentContactListBinding


class ContactListFragment : Fragment() {
    private lateinit var  binding: FragmentContactListBinding
    lateinit var contacts: List<Contact>

    companion object {
        const val itemClickedKey = "itemClickedKey"
        const val itemClickedPosition = "itemClickedPosition"
    }

    /**
     * Adapter.
     */
    private inner class ContactAdapter(val contacts: List<Contact>) :
        RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

        /**
         * View Holder.
         */
        private inner class ContactHolder(itemBinding: ContactListItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
            val name = itemBinding.contactListItemFullname

            init {
                itemBinding.root.setOnClickListener {
                    setFragmentResult(itemClickedKey, bundleOf(itemClickedPosition to bindingAdapterPosition))
                }
            }
        }

        /**
         * Create a view holder.
         */
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ContactHolder =
            ContactHolder(
                ContactListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        /**
         * Populate a view holder with data.
         */
        override fun onBindViewHolder(holder: ContactHolder, position: Int) {
            val contact = contacts[position]

            holder.name.text = contact.name
        }

        /**
         * The total quantity of items in the list.
         */
        override fun getItemCount(): Int = contacts.size

        /**
         * Called when a view holder is recycled.
         */
        override fun onViewRecycled(holder: ContactHolder) {
            super.onViewRecycled(holder)
            Log.d("APP", "Recycled holder at position ${holder.bindingAdapterPosition}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerviewContactList.apply {
            adapter = ContactAdapter(contacts)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }
}