package com.nexte.nexte.CommentsScene


import android.os.UserManager
import com.nexte.nexte.Entities.Comment.Comment
import java.text.SimpleDateFormat


/**
 * Interface to define Presentation Logic to Comment Class that
 * will be used to call this Interactor on other class layer
 */
interface CommentsPresentationLogic {

    /**
     * Method responsible to format comments data and send to view
     *
     * @param response contains unformatted data of a list of comments received from [CommentsModel]
     */
    fun presentComment(response: CommentsModel.GetCommentsRequest.Response)

    /**
     * Method responsible to format the new comment to be displayed on view
     *
     * @param response contains unformatted data of a comment received from [CommentsModel]
     */
    fun presentNewComment (response: CommentsModel.PublishCommentRequest.Response)

    /**
     * Method responsible to format the server answer to be displayed
     *
     * @param response contains integer that represents the server message
     */
    fun presentComplaint (response: CommentsModel.ComplaintRequest.Response)

    /**
     * Method responsible to format the new list of comments, without the excluded comment
     *
     * @param response contains unformatted data of list of comments
     */
    fun presentPositionToDelete(response: CommentsModel.DeleteCommentRequest.Response)
}

/**
 * Class needed to format response so the data can be displayed on activity
 *
 * @property viewController Reference to the activity where data will be displayed
 * on view
 */
class CommentsPresenter : CommentsPresentationLogic {

    var viewController: CommentsDisplayLogic? = null

    override fun presentComment(response: CommentsModel.GetCommentsRequest.Response) {

        val viewModel = CommentsModel.GetCommentsRequest.ViewModel(formatComment(response.comments))
        viewController?.displayComments(viewModel)
    }

    /**
     * Function that formatted the new comment wrote by user to send to View
     * @param response
     */
    override fun presentNewComment(response: CommentsModel.PublishCommentRequest.Response) {

        val newComment = response.newComment
        val dateToShow = SimpleDateFormat("EEE, dd 'of' LLL")
        val time = dateToShow.format(newComment.date)

        val formatted = CommentsModel.CommentFormatted(

                newComment.comment,
                time,
                newComment.author.name,
                newComment.author.photo
        )

        val viewModel = CommentsModel.PublishCommentRequest.ViewModel(formatted)

        viewController?.displayPublishedComment(viewModel)
    }

    /**
     * Function that formatted the alert message to send to View and define the message in case
     * of success ou error.
     * @param response
     */
    override fun presentComplaint(response: CommentsModel.ComplaintRequest.Response) {
        val message: String

        if (response.serverResponse == okMessage) {
            message = "Denúncia efetuada com sucesso"
        }
        else {
            message = "Erro ao conectar com o servidor"
        }
        val viewModel = CommentsModel.ComplaintRequest.ViewModel(message)

        viewController?.displayComplaintMessage(viewModel)
    }

    /**
     * Function that format the list of comments after the deletion of the comment indicated.
     *
     * @param response unformatted list of comments after deletion
     */
    override fun presentPositionToDelete(response: CommentsModel.DeleteCommentRequest.Response) {

        val viewModel = CommentsModel.DeleteCommentRequest.ViewModel(formatComment(response.delComments))
        viewController?.displayCommentsAfterDel(viewModel)
    }

    /**
     * Function that converts the MutableList Comment unformatted to
     * MutableList CommentFormatted.
     *
     * @param gameComments MutableList of unformatted comments
     * @return MutableList of formatted comments
     */
    private fun formatComment(gameComments: MutableList<Comment>) :
            MutableList<CommentsModel.CommentFormatted> {

        val commentsFormatted: MutableList<CommentsModel.CommentFormatted> = mutableListOf()

        for (gameComment in gameComments) {
            val user = com.nexte.nexte.Entities.User.UserManager().get(gameComment.userId!!)
            val dateToShow = SimpleDateFormat("EEE, dd 'of' LLL")
            val time = dateToShow.format(gameComment.date)

            val commentFormatted = CommentsModel.CommentFormatted(
                    gameComment.comment!!,
                    time,
                    user!!.name,
                    user.profilePicture!!.toInt())

            commentsFormatted.add(commentFormatted)
        }

        return commentsFormatted
    }


    companion object {
        const val okMessage = 200
    }
}